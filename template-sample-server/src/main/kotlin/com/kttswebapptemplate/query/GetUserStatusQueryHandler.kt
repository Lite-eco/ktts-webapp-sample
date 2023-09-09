package com.kttswebapptemplate.query

import com.kttswebapptemplate.domain.UserSession
import org.springframework.stereotype.Service

@Service
class GetUserStatusQueryHandler :
    QueryHandler.SessionHandler<GetUserStatusQuery, GetUserStatusQueryResponse>() {

    override fun handle(query: GetUserStatusQuery, userSession: UserSession) =
        GetUserStatusQueryResponse(userSession.status)
}
