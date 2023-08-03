package com.kttswebapptemplate.service.utils.random

import com.kttswebapptemplate.domain.TemplateSampleSecurityString
import com.kttswebapptemplate.domain.TemplateSampleStringId
import com.kttswebapptemplate.domain.TemplateSampleUuidId
import java.util.UUID
import org.apache.commons.text.CharacterPredicates
import org.apache.commons.text.RandomStringGenerator

open class RandomService(val idLogService: IdLogService? = null) {

    private val generator by lazy {
        RandomStringGenerator.Builder()
            .withinRange('0'.code, 'z'.code)
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .build()
    }

    inline fun <reified T : TemplateSampleUuidId> id(): T {
        val uuid = uuid()
        val id = T::class.constructors.first().call(uuid)
        idLogService?.log(id)
        return id
    }

    inline fun <reified T : TemplateSampleStringId> stringId(length: Int): T {
        val stringId = randomString(length)
        val id = T::class.constructors.first().call(stringId)
        idLogService?.log(id)
        return id
    }

    inline fun <reified T : TemplateSampleSecurityString> securityString(length: Int): T {
        val stringId = randomString(length)
        return T::class.constructors.first().call(stringId)
    }

    open fun uuid() = UUID.randomUUID()

    open fun randomString(length: Int) = generator.generate(length)
}
