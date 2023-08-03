package com.kttswebapptemplate.domain

import java.time.Instant

// TODO[tmpl] merge MimeType / FileExtension ?
enum class FileExtension(val postfix: String) {
    PNG("png")
}

// FIXME[tmpl] find elsewhere ? or we want a list of supported stuff
// in that case do type UserFileData
enum class MimeType(val fullType: String) {
    JAVASCRIPT("application/javascript"),
    JSON("application/json"),
    PDF("application/pdf")
}

data class UserFileData(val contentType: String, val file: ByteArray, val originalFilename: String)

data class UserFileReference(
    val id: UserFileId,
    val userId: UserId,
    val contentType: String,
    val originalFilename: String,
    val uploadDate: Instant
)
