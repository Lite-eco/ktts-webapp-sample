package com.kttswebapptemplate.domain

import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import org.springframework.security.crypto.password.PasswordEncoder

data class PlainStringPassword(protected val password: String) : SerializeAsString(password) {
    override fun toString() = "Password(${TemplateSampleStringUtils.filteredPassword})"

    fun isNotBlank() = password.isNotBlank()

    fun hashPassword(passwordEncoder: PasswordEncoder): HashedPassword {
        require(password.isNotBlank()) { "Password is blank" }
        return HashedPassword(passwordEncoder.encode(password.trim()))
    }

    fun passwordMatches(expextedPassword: HashedPassword, passwordEncoder: PasswordEncoder) =
        passwordEncoder.matches(password.trim(), expextedPassword.hash)
}

data class HashedPassword(val hash: String) {
    // [doc] even it's a hash, there's no reason to print it clean anywhere
    override fun toString() = "HashedPassword(${TemplateSampleStringUtils.filteredPassword})"
}

abstract class TemplateSampleSecurityString {

    companion object {
        const val displayBeforeMask = 8
        const val minimalLength = 20
    }

    open val rawString: String

    constructor(rawString: String) {
        this.rawString = rawString
        if (length() < minimalLength) {
            // because of mask
            throw IllegalArgumentException(
                "${javaClass.simpleName} minimal length is ${length()}, should not be less than $minimalLength")
        }
        if (rawString.length != length()) {
            throw IllegalArgumentException(
                "${javaClass.simpleName} length must be ${length()} (not ${rawString.length})")
        }
    }

    abstract fun length(): Int

    final override fun toString(): String {
        val mask =
            rawString.substring(0, displayBeforeMask).padEnd(length() - displayBeforeMask, '*')
        return "${javaClass.simpleName}($mask)"
    }
}
