package com.kttswebapptemplate.domain.http

// from org.springframework.http.HttpMethod
enum class HttpMethod(val method: String) {
    Get("GET"),
    Head("HEAD"),
    Post("POST"),
    Put("PUT"),
    Patch("PATCH"),
    Delete("DELETE"),
    Options("OPTIONS"),
    Trace("TRACE")
}
