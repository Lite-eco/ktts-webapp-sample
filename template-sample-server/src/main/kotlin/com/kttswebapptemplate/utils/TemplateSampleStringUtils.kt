package com.kttswebapptemplate.utils

import java.util.UUID
import org.apache.commons.lang3.StringUtils

object TemplateSampleStringUtils {

    val filteredPassword = "****** filteredPassword ******"

    fun removeAccents(value: String) = StringUtils.stripAccents(value)

    // TODO[tmpl] better impl
    fun serializeUuid(uuid: UUID) = uuid.toString().replace("-", "")
    // problem with this implementation with initial 0 (for instance
    // 0c9e5f71-40c4-4059-b4d9-bb3ec200b6bd)
    // fun serializeUuid(uuid: UUID) = uuid.mostSignificantBits.toHexString() +
    // uuid.leastSignificantBits.toHexString()

    // TODO[tmpl] better impl
    fun deserializeUuid(id: String) =
        if (id.length == 32) {
            UUID.fromString(
                "${id.substring(0, 8)}-${id.substring(8, 12)}-${id.substring(12, 16)}-" +
                    "${id.substring(16, 20)}-${id.substring(20)}")
        } else if (id.length == 36) {
            UUID.fromString(id)
        } else {
            throw IllegalAccessException("Can't unserialize UUID $id")
        }
}