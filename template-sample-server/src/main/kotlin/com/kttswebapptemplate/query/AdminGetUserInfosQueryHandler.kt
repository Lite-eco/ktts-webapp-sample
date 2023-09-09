package com.kttswebapptemplate.query

import com.kttswebapptemplate.domain.AdminUserInfos
import com.kttswebapptemplate.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class AdminGetUserInfosQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<AdminGetUserInfosQuery, AdminGetUserInfosQueryResponse>() {

    override fun handle(query: AdminGetUserInfosQuery) =
        AdminGetUserInfosQueryResponse(
            userDao.fetchOrNull(query.userId)?.let { AdminUserInfos.from(it) })
}
