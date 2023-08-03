package com.kttswebapptemplate.domain

fun <T> List<T>.firstAndOnlyOne(): T {
    if (size != 1) {
        throw IllegalArgumentException("Collection has $size elements.")
    }
    return first()
}
