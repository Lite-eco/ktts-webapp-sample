package com.kttswebapptemplate.domain

// FIXME[tmpl] test unicity ?
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Prefix(val value: String)

open class SerializeAsString(protected val internalValue: String) {
    fun serialize() = internalValue
}
