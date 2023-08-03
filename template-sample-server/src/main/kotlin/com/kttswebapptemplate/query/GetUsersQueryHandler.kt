package com.kttswebapptemplate.query

import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class GetUsersQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<GetUsersQuery, GetUsersQueryResponse>() {

    override fun handle(query: GetUsersQuery) =
        GetUsersQueryResponse(
            userDao
                .streamAll()
                .map { UserInfos(it.id, it.mail, it.displayName, it.roles) }
                .toList()
                .sortedBy { it.mail })
}
