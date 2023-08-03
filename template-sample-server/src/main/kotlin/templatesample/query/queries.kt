package templatesample.query

import com.fasterxml.jackson.annotation.JsonTypeInfo
import kttots.Shared
import templatesample.domain.UserId
import templatesample.domain.UserInfos

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
