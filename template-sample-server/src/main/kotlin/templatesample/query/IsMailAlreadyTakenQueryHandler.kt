package templatesample.query

import templatesample.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class IsMailAlreadyTakenQueryHandler(val userDao: UserDao) :
    QueryHandler.Handler<IsMailAlreadyTakenQuery, IsMailAlreadyTakenQueryResponse>() {

    override fun handle(query: IsMailAlreadyTakenQuery) =
        IsMailAlreadyTakenQueryResponse(userDao.doesMailExist(query.mail))
}
