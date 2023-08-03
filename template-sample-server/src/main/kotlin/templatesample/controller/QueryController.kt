package templatesample.controller

import java.net.URLDecoder
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import templatesample.query.GetUserInfosQuery
import templatesample.query.GetUserInfosQueryHandler
import templatesample.query.GetUsersQuery
import templatesample.query.GetUsersQueryHandler
import templatesample.query.IsMailAlreadyTakenQuery
import templatesample.query.IsMailAlreadyTakenQueryHandler
import templatesample.query.Query
import templatesample.query.QueryConfiguration
import templatesample.query.QueryHandler
import templatesample.query.QueryResponse
import templatesample.repository.user.UserDao
import templatesample.serialization.Serializer
import templatesample.service.user.UserSessionService
import templatesample.service.utils.TransactionIsolationService

@RestController
class QueryController(
    private val userDao: UserDao,
    private val userSessionService: UserSessionService,
    private val getUserInfosQueryHandler: GetUserInfosQueryHandler,
    private val getUsersQueryHandler: GetUsersQueryHandler,
    private val isMailAlreadyTakenQueryHandler: IsMailAlreadyTakenQueryHandler,
    private val transactionIsolationService: TransactionIsolationService,
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
            is GetUsersQuery -> getUsersQueryHandler
            is IsMailAlreadyTakenQuery -> isMailAlreadyTakenQueryHandler
        }.let { @Suppress("UNCHECKED_CAST") (it as QueryHandler<Query, QueryResponse>) }
}
