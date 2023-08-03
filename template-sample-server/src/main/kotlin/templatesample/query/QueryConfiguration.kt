package templatesample.query

import templatesample.domain.Role

object QueryConfiguration {

    fun role(query: Query): Role? =
        when (query) {
            is IsMailAlreadyTakenQuery -> null
        }
}
