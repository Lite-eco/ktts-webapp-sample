package com.kttswebapptemplate.query

import com.kttswebapptemplate.repository.user.UserDao
import com.kttswebapptemplate.service.user.UserService
import org.springframework.stereotype.Service

@Service
class IsMailAlreadyTakenQueryHandler(private val userDao: UserDao) :
    QueryHandler.Handler<IsMailAlreadyTakenQuery, IsMailAlreadyTakenQueryResponse>() {

    override fun handle(query: IsMailAlreadyTakenQuery) =
        IsMailAlreadyTakenQueryResponse(userDao.doesMailExist(UserService.cleanMail(query.mail)))
}
