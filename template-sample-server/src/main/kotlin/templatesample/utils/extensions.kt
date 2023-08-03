package templatesample.utils

import java.lang.reflect.InvocationTargetException
import java.util.UUID
import templatesample.domain.TemplateSampleSecurityString
import templatesample.domain.TemplateSampleStringId
import templatesample.domain.TemplateSampleUuidId

operator fun StringBuilder.plusAssign(adding: Any) {
    append(adding)
}

operator fun StringBuffer.plusAssign(adding: Any) {
    append(adding)
}

inline fun <reified R : TemplateSampleSecurityString> String.toSecurityString(): R =
    try {
        R::class.constructors.first().call(this)
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }

inline fun <reified R : TemplateSampleStringId> String.toTypeId(): R =
    try {
        R::class.constructors.first().call(this)
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }

inline fun <reified R : TemplateSampleUuidId> UUID.toTypeId(): R =
    try {
        R::class.constructors.first().call(this)
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }
