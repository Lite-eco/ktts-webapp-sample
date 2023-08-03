package com.kttswebapptemplate.domain

import com.kttswebapptemplate.utils.stringUuid
import java.util.UUID

interface TemplateSampleId<T> {
    val rawId: T
}

abstract class TemplateSampleUuidId : TemplateSampleId<UUID> {
    final override fun toString() = "${javaClass.simpleName}(${stringUuid()})"

    fun stringUuid() = rawId.stringUuid()
}

abstract class TemplateSampleStringId : TemplateSampleId<String> {
    override val rawId: String

    constructor(rawId: String) {
        this.rawId = rawId
        if (rawId.length != length()) {
            throw IllegalArgumentException("$rawId length must be ${length()}")
        }
    }

    abstract fun length(): Int

    final override fun toString() = "${javaClass.simpleName}($rawId)"
}

// TODO[tmpl][serialization] back as an inline class when Jackson supports it ?
// are data classes instead of inline class because of serialization "bugs" with Jackson
data class CommandLogId(override val rawId: UUID) : TemplateSampleUuidId()

data class DeploymentLogId(override val rawId: UUID) : TemplateSampleUuidId()

data class MailLogId(override val rawId: UUID) : TemplateSampleUuidId()

data class RequestErrorId(override val rawId: UUID) : TemplateSampleUuidId()

data class UserFileId(override val rawId: UUID) : TemplateSampleUuidId()

data class UserId(override val rawId: UUID) : TemplateSampleUuidId()

data class UserMailLogId(override val rawId: UUID) : TemplateSampleUuidId()

data class UserSessionId(override val rawId: UUID) : TemplateSampleUuidId()
