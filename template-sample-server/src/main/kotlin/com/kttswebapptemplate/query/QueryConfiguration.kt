package com.kttswebapptemplate.query

import com.kttswebapptemplate.domain.Role
import com.kttswebapptemplate.domain.Role.Admin

object QueryConfiguration {

    fun role(query: Query): Role? =
        when (query) {
            is GetUserInfosQuery -> Admin
            is GetUsersQuery -> Admin
            // do not put User because it's a query for non-validated-mail users
            is GetUserStatusQuery -> null
            is IsMailAlreadyTakenQuery -> null
        }
}
