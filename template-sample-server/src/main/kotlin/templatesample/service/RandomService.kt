package templatesample.service

import templatesample.domain.TemplateSampleSecurityString
import templatesample.domain.TemplateSampleStringId
import templatesample.domain.TemplateSampleUuidId
import java.util.UUID
import org.apache.commons.text.CharacterPredicates
import org.apache.commons.text.RandomStringGenerator

open class RandomService(val idLogService: IdLogService? = null) {

    val generator by lazy {
        RandomStringGenerator.Builder()
            .withinRange('0'.code, 'z'.code)
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .build()
    }

    inline fun <reified T : TemplateSampleUuidId> id(): T {
        @Suppress("DEPRECATION") val uuid = internalUuid()
        val id = T::class.constructors.first().call(uuid)
        idLogService?.log(id)
        return id
    }

    inline fun <reified T : TemplateSampleStringId> stringId(length: Int): T {
        @Suppress("DEPRECATION") val stringId = internalRandomString(length)
        val id = T::class.constructors.first().call(stringId)
        idLogService?.log(id)
        return id
    }

    inline fun <reified T : TemplateSampleSecurityString> securityString(length: Int): T {
        @Suppress("DEPRECATION") val stringId = internalRandomString(length)
        return T::class.constructors.first().call(stringId)
    }

    @Deprecated("Is for internal use only, exists because of reified id() & DummyRandomService")
    open fun internalUuid() = UUID.randomUUID()

    @Deprecated(
        "Is for internal use only, exists because of reified stringId() & DummyRandomService")
    open fun internalRandomString(length: Int) = generator.generate(length)
}
