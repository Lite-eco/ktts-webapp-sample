package com.kttswebapptemplate.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kttswebapptemplate.domain.SerializeAsString
import com.kttswebapptemplate.domain.TemplateSampleId
import com.kttswebapptemplate.domain.TemplateSampleSecurityString
import com.kttswebapptemplate.domain.TemplateSampleStringId
import com.kttswebapptemplate.domain.TemplateSampleUuidId
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import org.reflections.Reflections

object Serializer {

    private val idsPackage = TemplateSampleId::class.java.packageName

    private val reflections by lazy { Reflections(idsPackage) }

    val objectMapper: ObjectMapper = ObjectMapper().apply { configure(this) }

    fun serialize(value: Any): String = objectMapper.writeValueAsString(value)

    inline fun <reified T> deserialize(json: String): T = objectMapper.readValue(json)

    fun <T> deserialize(json: String, objectClass: Class<T>): T =
        objectMapper.readValue(json, objectClass)

    private fun configure(objectMapper: ObjectMapper) {
        val module =
            SimpleModule().apply {
                addSerializer(InstantSerializer())
                addDeserializer(Instant::class.java, InstantDeserializer())

                addSerializer(LocalDateSerializer())
                addDeserializer(LocalDate::class.java, LocalDateDeserializer())

                addSerializer(PlainStringPasswordSerializer())

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

    private fun addAllTemplateSampleStringIdsDeserializers(module: SimpleModule) {
        fun <T : TemplateSampleStringId> addDeserializer(
            module: SimpleModule,
            idKClass: KClass<T>
        ) {
            module.addDeserializer(idKClass.java, TemplateSampleStringIdDeserializer(idKClass))
            module.addKeyDeserializer(
                idKClass.java, TemplateSampleStringIdKeyDeserializer(idKClass))
        }

        val idClasses: Set<Class<out TemplateSampleStringId>> =
            reflections.getSubTypesOf(TemplateSampleStringId::class.java)
        idClasses.forEach {
            @Suppress("UNCHECKED_CAST")
            addDeserializer(
                module, Reflection.createKotlinClass(it) as KClass<out TemplateSampleStringId>)
        }
    }

    private fun addAllTemplateSampleUuidIdsDeserializers(module: SimpleModule) {
        fun <T : TemplateSampleUuidId> addDeserializer(module: SimpleModule, idKClass: KClass<T>) {
            module.addDeserializer(idKClass.java, TemplateSampleUuidIdDeserializer(idKClass))
            module.addKeyDeserializer(idKClass.java, TemplateSampleUuidIdKeyDeserializer(idKClass))
        }

        val idClasses: Set<Class<out TemplateSampleUuidId>> =
            reflections.getSubTypesOf(TemplateSampleUuidId::class.java)
        idClasses.forEach {
            @Suppress("UNCHECKED_CAST")
            addDeserializer(
                module, Reflection.createKotlinClass(it) as KClass<out TemplateSampleUuidId>)
        }
    }

    private fun addAllTemplateSampleSecurityStringDeserializers(module: SimpleModule) {
        fun <T : TemplateSampleSecurityString> addDeserializer(
            module: SimpleModule,
            idKClass: KClass<T>
        ) {
            module.addDeserializer(
                idKClass.java, TemplateSampleSecurityStringDeserializer(idKClass))
            module.addKeyDeserializer(
                idKClass.java, TemplateSampleSecurityStringKeyDeserializer(idKClass))
        }

        val idClasses: Set<Class<out TemplateSampleSecurityString>> =
            reflections.getSubTypesOf(TemplateSampleSecurityString::class.java)
        idClasses.forEach {
            @Suppress("UNCHECKED_CAST")
            addDeserializer(
                module,
                Reflection.createKotlinClass(it) as KClass<out TemplateSampleSecurityString>)
        }
    }

    private fun addAllSerializeAsStringDeserializers(module: SimpleModule) {
        fun <T : SerializeAsString> addDeserializer(module: SimpleModule, idKClass: KClass<T>) {
            module.addDeserializer(idKClass.java, SerializeAsStringDeserializer(idKClass))
            module.addKeyDeserializer(idKClass.java, SerializeAsStringKeyDeserializer(idKClass))
        }

        val idClasses: Set<Class<out SerializeAsString>> =
            reflections.getSubTypesOf(SerializeAsString::class.java)
        idClasses.forEach {
            @Suppress("UNCHECKED_CAST")
            addDeserializer(
                module, Reflection.createKotlinClass(it) as KClass<out SerializeAsString>)
        }
    }
}
