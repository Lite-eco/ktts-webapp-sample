package com.kttswebapptemplate.utils

import com.kttswebapptemplate.domain.TemplateSampleSecurityString
import com.kttswebapptemplate.domain.TemplateSampleStringId
import com.kttswebapptemplate.domain.TemplateSampleUuidId
import java.lang.reflect.InvocationTargetException
import java.util.UUID

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
