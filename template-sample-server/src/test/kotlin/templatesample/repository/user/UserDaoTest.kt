package templatesample.repository.user

import templatesample.TestData
import templatesample.domain.HashedPassword
import templatesample.error.MailAlreadyRegisteredException
import templatesample.service.RandomService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class UserDaoTest(
    @Autowired val userDao: UserDao,
    @Autowired val randomService: RandomService
) {

    @Test
    fun `test conflict`() {
        userDao.insert(TestData.dummyUser(randomService.id()), HashedPassword("sdv"))
        Assertions.assertThrows(MailAlreadyRegisteredException::class.java) {
            userDao.insert(TestData.dummyUser(randomService.id()), HashedPassword("sdv"))
        }
    }
}
