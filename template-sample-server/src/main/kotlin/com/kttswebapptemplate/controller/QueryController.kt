package com.kttswebapptemplate.controller

import com.kttswebapptemplate.query.GetUserInfosQuery
import com.kttswebapptemplate.query.GetUserInfosQueryHandler
import com.kttswebapptemplate.query.GetUserStatusQuery
import com.kttswebapptemplate.query.GetUserStatusQueryHandler
import com.kttswebapptemplate.query.GetUsersQuery
import com.kttswebapptemplate.query.GetUsersQueryHandler
import com.kttswebapptemplate.query.IsMailAlreadyTakenQuery
import com.kttswebapptemplate.query.IsMailAlreadyTakenQueryHandler
import com.kttswebapptemplate.query.Query
import com.kttswebapptemplate.query.QueryConfiguration
import com.kttswebapptemplate.query.QueryHandler
import com.kttswebapptemplate.query.QueryResponse
import com.kttswebapptemplate.serialization.Serializer
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.service.utils.TransactionIsolationService
import jakarta.servlet.http.HttpServletRequest
import java.net.URLDecoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryController(
    private val transactionIsolationService: TransactionIsolationService,
    private val userSessionService: UserSessionService,
    private val getUserInfosQueryHandler: GetUserInfosQueryHandler,
    private val getUsersQueryHandler: GetUsersQueryHandler,
    private val getUserStatusQueryHandler: GetUserStatusQueryHandler,
    private val isMailAlreadyTakenQueryHandler: IsMailAlreadyTakenQueryHandler,
) {

    @GetMapping("/query")
    fun handle(request: HttpServletRequest): QueryResponse {
        val jsonQuery = URLDecoder.decode(request.queryString, Charsets.UTF_8.name())
        val query = Serializer.deserialize<Query>(jsonQuery)
        userSessionService.verifyStatusAndRole(
            QueryConfiguration.role(query), request.remoteAddr, query.javaClass)
        val handler = handler(query)
        val userSession = userSessionService.getUserSessionIfAuthenticated()
        return transactionIsolationService.executeReadOnly { handler.doHandle(query, userSession) }
    }

    private fun handler(query: Query) =
        when (query) {
            is GetUserInfosQuery -> getUserInfosQueryHandler
            GetUsersQuery -> getUsersQueryHandler
            GetUserStatusQuery -> getUserStatusQueryHandler
            is IsMailAlreadyTakenQuery -> isMailAlreadyTakenQueryHandler
        }.let { @Suppress("UNCHECKED_CAST") (it as QueryHandler<Query, QueryResponse>) }
}
