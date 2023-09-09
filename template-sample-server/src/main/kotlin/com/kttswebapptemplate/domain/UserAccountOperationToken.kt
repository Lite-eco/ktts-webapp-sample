package com.kttswebapptemplate.domain

import kt2ts.annotation.GenerateTypescript

enum class UserAccountOperationTokenType {
    ValidateMail,
}

// TODO annotation should not be needed here
@GenerateTypescript
data class UserAccountOperationToken(override val rawString: String) :
    TemplateSampleSecurityString(rawString) {
    companion object {
        val length = 40
    }

    override fun length() = length
}
