package com.kttswebapptemplate.utils

import com.kttswebapptemplate.domain.TemplateSampleId
import com.kttswebapptemplate.domain.TemplateSampleSecurityString
import com.kttswebapptemplate.domain.TemplateSampleUuidId
import java.lang.reflect.InvocationTargetException
import java.util.UUID
import kotlin.reflect.full.starProjectedType
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.core.env.ConfigurableEnvironment

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

// "cache" because is not instantaneous
val TemplateSampleUuidIdStarProjectedType = TemplateSampleUuidId::class.starProjectedType

inline fun <reified R : TemplateSampleId<*>> String.toTypeId(): R =
    try {
        if (TemplateSampleUuidIdStarProjectedType in R::class.supertypes) {
            R::class.constructors.first().call(this.uuid())
        } else {
            R::class.constructors.first().call(this)
        }
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }

inline fun <reified R : TemplateSampleUuidId> UUID.toTypeId(): R =
    try {
        R::class.constructors.first().call(this)
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }

// UUID(29d902e1-c234-4e54-b305-8b7a885a560e) -> 29d902e1c2344e54b3058b7a885a560e
fun UUID.stringUuid(): String =
    toString().let {
        it.substring(0, 8) +
            it.substring(9, 13) +
            it.substring(14, 18) +
            it.substring(19, 23) +
            it.substring(24, 36)
    }

// 29d902e1c2344e54b3058b7a885a560e -> UUID(29d902e1-c234-4e54-b305-8b7a885a560e)
fun String.uuid(): UUID =
    if (length == 32) {
        UUID.fromString(
            "${substring(0, 8)}-${substring(8, 12)}-${substring(12, 16)}-" +
                "${substring(16, 20)}-${substring(20, 32)}")
    } else if (length == 36) {
        UUID.fromString(
            "${substring(0, 8)}-${substring(9, 13)}-${substring(14, 18)}-" +
                "${substring(19, 23)}-${substring(24, 36)}")
    } else {
        throw IllegalArgumentException(this)
    }

fun BeanDefinitionDsl.BeanSupplierContext.config(property: String) =
    ref<ConfigurableEnvironment>().resolveRequiredPlaceholders("\${$property}")
