package com.kttswebapptemplate.domain

data class MimeType(val type: String) : SerializeAsString(type) {
    companion object {
        val ApplicationJavascript = MimeType("application/javascript")
        val ApplicationJson = MimeType("application/json")
    }
}
