package com.kttswebapptemplate.query

import com.kttswebapptemplate.domain.UserInfos
import com.kttswebapptemplate.repository.user.UserDao
import org.springframework.stereotype.Service

@Service
class GetUserInfosQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<GetUserInfosQuery, GetUserInfosQueryResponse>() {

    override fun handle(query: GetUserInfosQuery) =
        GetUserInfosQueryResponse(
            userDao.fetchOrNull(query.userId)?.let {
                UserInfos(it.id, it.mail, it.displayName, it.status, it.role)
            })
}
