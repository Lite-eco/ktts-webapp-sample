package templatesample.query

import org.springframework.stereotype.Service
import templatesample.domain.UserInfos
import templatesample.repository.user.UserDao

@Service
class GetUsersQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<GetUsersQuery, GetUsersQueryResponse>() {

    override fun handle(query: GetUsersQuery) =
        GetUsersQueryResponse(
            userDao
                .streamAll()
                .map { UserInfos(it.id, it.mail, it.displayName, it.roles) }
                .toList())
}
