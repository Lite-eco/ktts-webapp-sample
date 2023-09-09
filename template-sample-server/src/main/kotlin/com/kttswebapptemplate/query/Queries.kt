package com.kttswebapptemplate.query

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.kttswebapptemplate.domain.AdminUserInfos
import com.kttswebapptemplate.domain.UserId
import com.kttswebapptemplate.domain.UserStatus
import kt2ts.annotation.GenerateTypescript

@GenerateTypescript
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class Query

@GenerateTypescript sealed class QueryResponse

data class AdminGetUserInfosQuery(val userId: UserId) : Query()

data class AdminGetUserInfosQueryResponse(val userInfos: AdminUserInfos?) : QueryResponse()

data object AdminGetUsersQuery : Query()

data class AdminGetUsersQueryResponse(val users: List<AdminUserInfos>) : QueryResponse()

data object GetUserStatusQuery : Query()

data class GetUserStatusQueryResponse(val status: UserStatus) : QueryResponse()

data class IsMailAlreadyTakenQuery(val mail: String) : Query()

data class IsMailAlreadyTakenQueryResponse(val alreadyTaken: Boolean) : QueryResponse()
