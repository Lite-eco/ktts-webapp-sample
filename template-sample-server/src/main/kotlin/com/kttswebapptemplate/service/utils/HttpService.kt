package com.kttswebapptemplate.service.utils

import com.kttswebapptemplate.domain.Uri
import com.kttswebapptemplate.domain.http.HttpHeader
import com.kttswebapptemplate.domain.http.HttpMethod
import com.kttswebapptemplate.domain.http.HttpStatus
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.springframework.stereotype.Service

@Service
class HttpService(val okHttpClient: OkHttpClient) {

    companion object {
        const val jsonMediaType = "application/json; charset=utf-8"
    }

    data class Response(val code: HttpStatus, private val responseBody: ResponseBody?) {
        // [doc] let it NOT lazy, OkHttp closes body after execute()
        private val buffer = responseBody?.bytes()

        val body: ByteArray?
            get() = buffer

        val bodyString: String?
            get() = buffer?.toString(Charsets.UTF_8)
    }

    data class EmptyResponse(val code: Int)

    open fun execute(method: HttpMethod, url: Uri, vararg headers: Pair<HttpHeader, String>) =
        execute(method, url, null, *headers)

    open fun execute(
        method: HttpMethod,
        url: Uri,
        body: String,
        vararg headers: Pair<HttpHeader, String>
    ) = execute(method, url, body.toRequestBody(), *headers)

    open fun execute(
        method: HttpMethod,
        url: Uri,
        body: RequestBody?,
        vararg headers: Pair<HttpHeader, String>
    ): Response =
        Request.Builder()
            .apply {
                url(url.path)
                header(HttpHeader.Accept.header, jsonMediaType)
                header(HttpHeader.ContentType.header, jsonMediaType)
                headers.forEach { header(it.first.header, it.second) }
                method(method.method, body)
            }
            .let { okHttpClient.newCall(it.build()) }
            .execute()
            .use { Response(HttpStatus.of(it.code), it.body) }
}
