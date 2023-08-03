package templatesample.query

import templatesample.domain.Role
import templatesample.domain.Role.Admin

object QueryConfiguration {

    fun role(query: Query): Role? =
        when (query) {
            is GetUserInfosQuery -> Admin
            is GetUsersQuery -> Admin
            is IsMailAlreadyTakenQuery -> null
        }
}
