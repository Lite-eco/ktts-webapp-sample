package templatesample.controller

import templatesample.query.*
import templatesample.repository.user.UserDao
import templatesample.serialization.Serializer
import templatesample.service.user.UserSessionService
import templatesample.service.utils.TransactionIsolationService
import java.net.URLDecoder
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryController(
    val userDao: UserDao,
    val userSessionService: UserSessionService,
    val getUserInfosQueryHandler: GetUserInfosQueryHandler,
    val getUsersListQueryHandler: GetUsersListQueryHandler,
    val isMailAlreadyTakenQueryHandler: IsMailAlreadyTakenQueryHandler,
    val transactionIsolationService: TransactionIsolationService,
) {

    @GetMapping("/query")
    fun handle(request: HttpServletRequest): QueryResponse {
        val jsonQuery = URLDecoder.decode(request.queryString, Charsets.UTF_8.name())
        val query = Serializer.deserialize<Query>(jsonQuery)
        userSessionService.verifyRoleOrFail(
            QueryConfiguration.role(query), request.remoteAddr, query.javaClass)
        val handler = handler(query)
        val userSession =
            if (userSessionService.isAuthenticated()) userSessionService.getUserSession() else null
        return transactionIsolationService.executeReadOnly { handler.doHandle(query, userSession) }
    }

    private fun handler(query: Query) =
        when (query) {
            is GetUserInfosQuery -> getUserInfosQueryHandler
            is GetUsersListQuery -> getUsersListQueryHandler
            is IsMailAlreadyTakenQuery -> isMailAlreadyTakenQueryHandler
        }.let { @Suppress("UNCHECKED_CAST") (it as QueryHandler<Query, QueryResponse>) }
}
