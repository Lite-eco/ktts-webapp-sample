package com.kttswebapptemplate.service.utils.random

import com.kttswebapptemplate.domain.TemplateSampleId
import com.kttswebapptemplate.utils.TemplateSampleStringUtils
import java.util.UUID

class IdLogService {

    data class IdLogging(var enableLogging: Boolean, val list: MutableList<TemplateSampleId<*>>?) {
        init {
            if (enableLogging) {
                require(list != null)
            } else {
                require(list == null)
            }
        }
    }

    private val list = ThreadLocal.withInitial { IdLogging(false, null) }

    fun enableLogging() {
        list.set(IdLogging(true, mutableListOf()))
    }

    fun log(id: TemplateSampleId<*>) {
        list.get()?.let {
            if (it.enableLogging) {
                if (it.list == null) {
                    throw RuntimeException()
                }
                it.list.add(id)
            }
        }
    }

    // TODO[tmpl] would be faster avec un StringBuffer
    fun getIdsString(): String =
        (list.get().list ?: throw RuntimeException())
            .map {
                val rawIdString =
                    it.rawId.let {
                        when (it) {
                            is UUID -> TemplateSampleStringUtils.serializeUuid(it)
                            else -> it.toString()
                        }
                    }
                it.javaClass.simpleName + " " + rawIdString
            }
            .joinToString(separator = "\n")

    fun clean() = list.set(null)
}
