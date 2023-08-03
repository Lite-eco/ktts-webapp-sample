package com.kttswebapptemplate.repository.user

import com.kttswebapptemplate.TestData
import com.kttswebapptemplate.domain.HashedPassword
import com.kttswebapptemplate.error.MailAlreadyRegisteredException
import com.kttswebapptemplate.service.utils.DateService
import com.kttswebapptemplate.service.utils.random.RandomService
import com.kttswebapptemplate.utils.PostgresIntegrationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class UserDaoTest(
    @Autowired val userDao: UserDao,
    @Autowired val randomService: RandomService,
    @Autowired val dateService: DateService
) : PostgresIntegrationTest {

    @Test
    fun `test insert conflict`() {
        userDao.insert(TestData.dummyUser(randomService.id()), HashedPassword("sdv"))
        Assertions.assertThrows(MailAlreadyRegisteredException::class.java) {
            userDao.insert(TestData.dummyUser(randomService.id()), HashedPassword("sdv"))
        }
    }

    @Test
    fun `test update mail conflict`() {
        val u1 = TestData.dummyUser(randomService.id())
        userDao.insert(u1, HashedPassword("sdv"))
        val u2 =
            TestData.dummyUser(randomService.id())
                .copy(mail = "another mail", displayName = "another")
        userDao.insert(u2, HashedPassword("sdv"))
        Assertions.assertThrows(MailAlreadyRegisteredException::class.java) {
            userDao.updateMail(u2.id, u1.mail, dateService.now())
        }
    }
}
