package com.kttswebapptemplate.utils

import org.apache.commons.lang3.StringUtils

object TemplateSampleStringUtils {

    val filteredPassword = "****** filteredPassword ******"

    fun removeAccents(value: String) = StringUtils.stripAccents(value)
}
