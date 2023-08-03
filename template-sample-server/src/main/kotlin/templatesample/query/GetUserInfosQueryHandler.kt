package templatesample.query

import templatesample.domain.UserInfos
import templatesample.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class GetUserInfosQueryHandler(val userDao: UserDao) :
    QueryHandler.Handler<GetUserInfosQuery, GetUserInfosQueryResponse>() {

    override fun handle(query: GetUserInfosQuery) =
        GetUserInfosQueryResponse(
            userDao.fetchOrNull(query.userId)?.let {
                UserInfos(it.id, it.mail, it.displayName, it.roles)
            })
}
