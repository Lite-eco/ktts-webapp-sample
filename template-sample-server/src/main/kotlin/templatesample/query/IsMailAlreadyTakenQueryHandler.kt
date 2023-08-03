package templatesample.query

import org.springframework.stereotype.Service
import templatesample.repository.user.UserDao

@Service
class IsMailAlreadyTakenQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<IsMailAlreadyTakenQuery, IsMailAlreadyTakenQueryResponse>() {

    override fun handle(query: IsMailAlreadyTakenQuery) =
        IsMailAlreadyTakenQueryResponse(userDao.doesMailExist(query.mail))
}
