package com.kttswebapptemplate.database.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class SqlDependenciesResolverTest {

    // TODO a dependency fail
    @Test
    fun test() {
        val userSql =
            """
CREATE TABLE app_user
(
    id UUID PRIMARY KEY,
    file UUID
);

ALTER TABLE app_user ADD FOREIGN KEY(file) REFERENCES user_file(id);

CREATE INDEX ON app_user (mail);
        """
                .trimIndent()
        val userFileSql =
            """
-- comments are simply removed
CREATE TABLE user_file
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);
        """
                .trimIndent()
        assertEquals(
            """
CREATE TABLE app_user
(
    id UUID PRIMARY KEY,
    file UUID
);

CREATE TABLE user_file
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);

ALTER TABLE app_user ADD FOREIGN KEY(file) REFERENCES user_file(id);

CREATE INDEX ON app_user (mail);
        """
                .trimIndent(),
            SqlDependenciesResolver.resolveSql(
                    listOf(
                        SqlDependenciesResolver.SqlFile("1", userSql),
                        SqlDependenciesResolver.SqlFile("2", userFileSql)))
                .map { it.file.sql + ";" }
                .joinToString(separator = "\n\n")
                .trim())
    }

    @Test
    fun `test cyclic reference`() {
        val sql1 =
            """
CREATE TABLE test1
(
    id UUID PRIMARY KEY,
    test_id UUID,
    FOREIGN KEY (test_id) REFERENCES test2 (id)
);
"""
        val sql2 =
            """
CREATE TABLE test2
(
    id UUID PRIMARY KEY,
    test_id UUID,
    FOREIGN KEY (test_id) REFERENCES test3 (id)
);
"""
        val sql3 =
            """
CREATE TABLE test3
(
    id UUID PRIMARY KEY,
    test_id UUID,
    FOREIGN KEY (test_id) REFERENCES test1 (id)
);
"""
        val thrown =
            assertThrows(IllegalArgumentException::class.java) {
                SqlDependenciesResolver.resolveSql(
                    listOf(
                        SqlDependenciesResolver.SqlFile("1", sql1),
                        SqlDependenciesResolver.SqlFile("2", sql2),
                        SqlDependenciesResolver.SqlFile("3", sql3)))
            }
        assertEquals(
            "Cyclic reference test1 -> test2 -> test3 -> test1. " +
                "Think about using an 'ALTER TABLE [...] ADD FOREIGN KEY [...]' query.",
            thrown.message)
    }

    @Test
    fun `test missing dependency`() {
        val sql1 =
            """
CREATE TABLE test1
(
    id UUID PRIMARY KEY,
    test_id UUID,
    FOREIGN KEY (test_id) REFERENCES test2 (id)
);
"""
        val thrown =
            assertThrows(IllegalArgumentException::class.java) {
                SqlDependenciesResolver.resolveSql(
                    listOf(SqlDependenciesResolver.SqlFile("1", sql1)))
            }
        assertEquals(
            "Table ${SqlDependenciesResolver.TableName("public", "test1")} references test2 which isn't described.",
            thrown.message)
    }
}
