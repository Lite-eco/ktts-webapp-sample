package templatesample.query

import templatesample.domain.Role
import templatesample.domain.Role.*

object QueryConfiguration {

    fun role(query: Query): Role? =
        when (query) {
            is GetUserInfosQuery -> Admin
            is GetUsersListQuery -> Admin
            is IsMailAlreadyTakenQuery -> null
        }
}
