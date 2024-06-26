package com.kttswebapptemplate.query

import com.kttswebapptemplate.domain.AdminUserInfos
import com.kttswebapptemplate.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class AdminGetUsersQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<AdminGetUsersQuery, AdminGetUsersQueryResponse>() {

    override fun handle(query: AdminGetUsersQuery) =
        AdminGetUsersQueryResponse(
            userDao.fetchAll().map { AdminUserInfos.from(it) }.sortedByDescending { it.signupDate })
}
