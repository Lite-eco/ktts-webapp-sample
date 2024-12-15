package com.kttswebapptemplate.domain

data class MimeType(val type: String) : SerializeAsString(type) {
    companion object {
        val ApplicationJson = MimeType("application/json")
        val TextJavascript = MimeType("text/javascript")
    }
}
