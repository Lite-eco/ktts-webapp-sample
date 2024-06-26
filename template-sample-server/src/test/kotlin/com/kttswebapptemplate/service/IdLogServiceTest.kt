package com.kttswebapptemplate.service

import com.kttswebapptemplate.domain.TestPrefixSecurityString
import com.kttswebapptemplate.domain.TestPrefixStringId
import com.kttswebapptemplate.domain.TestPrefixUuidId
import com.kttswebapptemplate.domain.TestSecurityString
import com.kttswebapptemplate.domain.TestStringId
import com.kttswebapptemplate.domain.TestUuidId
import com.kttswebapptemplate.service.utils.random.DummyRandomService
import com.kttswebapptemplate.service.utils.random.IdLogService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IdLogServiceTest {

    val service = IdLogService()

    @Test
    fun `test ids string`() {
        val randomService = DummyRandomService(service)
        service.enableLogging()
        randomService.id<TestUuidId>()
        randomService.id<TestPrefixUuidId>()
        randomService.stringId<TestStringId>(TestStringId.length)
        randomService.stringId<TestPrefixStringId>(TestStringId.length)
        randomService.securityString<TestSecurityString>(TestStringId.length)
        randomService.securityString<TestPrefixSecurityString>(TestStringId.length)
        assertEquals(
            """
TestUuidId f89ac98cacc54eea98561cc9658a6663
TestPrefixUuidId bf23ea927f8a4e14a9a11b6604f979eb
TestStringId 03LOeGFj5ZibyaqdISrO
TestPrefixStringId rSSPouSEbuXmda1rHUFC
        """
                .trimIndent(),
            service.getIdsString())
    }

    @Test
    fun `exhaust uuids buffer`() {
        val randomService = DummyRandomService(service)
        try {
            try {
                (1..20).forEach { randomService.id<TestUuidId>() }
            } catch (e: Exception) {
                Assertions.fail()
            }
            randomService.id<TestUuidId>()
            Assertions.fail()
        } catch (e: Exception) {
            assertEquals("DummyRandomService has no more uuids (cursor: 21)", e.message)
        }
    }

    @Test
    fun `exhaust string ids buffer`() {
        val randomService = DummyRandomService(service)
        try {
            try {
                (1..20).forEach { randomService.stringId<TestStringId>(TestStringId.length) }
            } catch (e: Exception) {
                Assertions.fail()
            }
            randomService.stringId<TestStringId>(TestStringId.length)
            Assertions.fail()
        } catch (e: Exception) {
            assertEquals("DummyRandomService has no more string ids (cursor: 21)", e.message)
        }
    }
}
