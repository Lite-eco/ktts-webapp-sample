package templatesample.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import org.reflections.Reflections
import templatesample.domain.PlainStringPassword
import templatesample.domain.SerializeAsString
import templatesample.domain.TemplateSampleId
import templatesample.domain.TemplateSampleSecurityString
import templatesample.domain.TemplateSampleStringId
import templatesample.domain.TemplateSampleUuidId

object Serializer {

    val idsPackage = TemplateSampleId::class.java.packageName

    private val reflections by lazy { Reflections(idsPackage) }

    val objectMapper: ObjectMapper = ObjectMapper().apply { configure(this) }

    fun serialize(value: Any): String = objectMapper.writeValueAsString(value)

    inline fun <reified T> deserialize(json: String): T = objectMapper.readValue(json)

    fun <T> deserialize(json: String, objectClass: Class<T>): T =
        objectMapper.readValue(json, objectClass)

    fun configure(objectMapper: ObjectMapper) {
        val module =
            SimpleModule().apply {
                addSerializer(InstantSerializer())
                addDeserializer(Instant::class.java, InstantDeserializer())

                addSerializer(LocalDateSerializer())
                addDeserializer(LocalDate::class.java, LocalDateDeserializer())

                addSerializer(PlainStringPasswordSerializer())
                addDeserializer(PlainStringPassword::class.java, PlainStringPasswordDeserializer())

                // TODO[tmpl][serialization] handle all the null
                addSerializer(UuidSerializer())
                addDeserializer(UUID::class.java, UuidDeserializer())
                addKeySerializer(UUID::class.java, UuidKeySerializer())
                addKeyDeserializer(UUID::class.java, UuidKeyDeserializer())

                addSerializer(ZoneIdSerializer())
                addDeserializer(ZoneId::class.java, ZoneIdDeserializer())

                addSerializer(TemplateSampleSecurityStringSerializer())
                addKeySerializer(
                    TemplateSampleSecurityString::class.java,
                    TemplateSampleSecurityStringKeySerializer())
                addAllTemplateSampleSecurityStringDeserializers(this)

                // TODO[tmpl][serialization] about data class
                addSerializer(TemplateSampleUuidIdSerializer())
                addKeySerializer(
                    TemplateSampleUuidId::class.java, TemplateSampleUuidIdKeySerializer())
                addAllTemplateSampleUuidIdsDeserializers(this)

                addSerializer(TemplateSampleStringIdSerializer())
                addKeySerializer(
                    TemplateSampleStringId::class.java, TemplateSampleStringIdKeySerializer())
                addAllTemplateSampleStringIdsDeserializers(this)

                addSerializer(SerializeAsStringSerializer())
                addKeySerializer(SerializeAsString::class.java, SerializeAsStringKeySerializer())
                addAllSerializeAsStringDeserializers(this)
            }

        objectMapper.registerModule(module)
        objectMapper.registerKotlinModule()
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    fun addAllTemplateSampleStringIdsDeserializers(module: SimpleModule) {
        fun <T : TemplateSampleStringId> addDeserializer(
            module: SimpleModule,
            idKclass: KClass<T>
        ) {
            module.addDeserializer(idKclass.java, TemplateSampleStringIdDeserializer(idKclass))
            module.addKeyDeserializer(
                idKclass.java, TemplateSampleStringIdKeyDeserializer(idKclass))
        }

        val idClasses: Set<Class<out TemplateSampleStringId>> =
            reflections.getSubTypesOf(TemplateSampleStringId::class.java)
        idClasses.forEach {
            addDeserializer(
                module, Reflection.createKotlinClass(it) as KClass<out TemplateSampleStringId>)
        }
    }

    fun addAllTemplateSampleUuidIdsDeserializers(module: SimpleModule) {
        fun <T : TemplateSampleUuidId> addDeserializer(module: SimpleModule, idKclass: KClass<T>) {
            module.addDeserializer(idKclass.java, TemplateSampleUuidIdDeserializer(idKclass))
            module.addKeyDeserializer(idKclass.java, TemplateSampleUuidIdKeyDeserializer(idKclass))
        }

        val idClasses: Set<Class<out TemplateSampleUuidId>> =
            reflections.getSubTypesOf(TemplateSampleUuidId::class.java)
        idClasses.forEach {
            addDeserializer(
                module, Reflection.createKotlinClass(it) as KClass<out TemplateSampleUuidId>)
        }
    }

    fun addAllTemplateSampleSecurityStringDeserializers(module: SimpleModule) {
        fun <T : TemplateSampleSecurityString> addDeserializer(
            module: SimpleModule,
            idKclass: KClass<T>
        ) {
            module.addDeserializer(
                idKclass.java, TemplateSampleSecurityStringDeserializer(idKclass))
            module.addKeyDeserializer(
                idKclass.java, TemplateSampleSecurityStringKeyDeserializer(idKclass))
        }

        val idClasses: Set<Class<out TemplateSampleSecurityString>> =
            reflections.getSubTypesOf(TemplateSampleSecurityString::class.java)
        idClasses.forEach {
            addDeserializer(
                module,
                Reflection.createKotlinClass(it) as KClass<out TemplateSampleSecurityString>)
        }
    }

    fun addAllSerializeAsStringDeserializers(module: SimpleModule) {
        fun <T : SerializeAsString> addDeserializer(module: SimpleModule, idKclass: KClass<T>) {
            module.addDeserializer(idKclass.java, SerializeAsStringDeserializer(idKclass))
            module.addKeyDeserializer(idKclass.java, SerializeAsStringKeyDeserializer(idKclass))
        }

        val idClasses: Set<Class<out SerializeAsString>> =
            reflections.getSubTypesOf(SerializeAsString::class.java)
        idClasses.forEach {
            addDeserializer(
                module, Reflection.createKotlinClass(it) as KClass<out SerializeAsString>)
        }
    }
}
