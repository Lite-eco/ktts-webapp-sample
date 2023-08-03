package com.kttswebapptemplate.query

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserInfos
import kttots.Shared

@Shared
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class Query

@Shared sealed class QueryResponse

data class GetUserInfosQuery(val userId: UserId) : Query()

data class GetUserInfosQueryResponse(val userInfos: UserInfos?) : QueryResponse()

class GetUsersQuery : Query()

data class GetUsersQueryResponse(val users: List<UserInfos>) : QueryResponse()

data class IsMailAlreadyTakenQuery(val mail: String) : Query()

data class IsMailAlreadyTakenQueryResponse(val alreadyTaken: Boolean) : QueryResponse()
