package templatesample.query

import templatesample.domain.UserInfos
import templatesample.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class GetUsersListQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<GetUsersListQuery, GetUsersListQueryResponse>() {

    override fun handle(query: GetUsersListQuery) =
        GetUsersListQueryResponse(
            userDao.streamAll().toList().map {
                UserInfos(it.id, it.mail, it.displayName, it.roles)
            })
}
