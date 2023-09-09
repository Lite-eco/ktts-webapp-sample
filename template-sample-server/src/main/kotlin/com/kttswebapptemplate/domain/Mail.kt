package com.kttswebapptemplate.domain

import com.fasterxml.jackson.annotation.JsonTypeInfo

data class Mail(
    val sender: Contact,
    val recipient: Contact,
    val subject: String,
    val content: String
) {
    data class Contact(val name: String, val mail: String)
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
sealed class MailData {
    data class AccountMailValidation(val displayName: String, val url: Uri) : MailData()

    data class LostPassword(val url: Uri) : MailData()
}
