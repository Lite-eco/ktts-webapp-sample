package com.kttswebapptemplate.controller

import com.kttswebapptemplate.domain.UserFileId
import com.kttswebapptemplate.repository.user.UserFileDao
import com.kttswebapptemplate.service.user.UserSessionService
import com.kttswebapptemplate.utils.toTypeId
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class UserFileController(
    private val userFileDao: UserFileDao,
    private val userSessionService: UserSessionService
) {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/user-file/{id}")
    fun getUserFile(@PathVariable id: String): ResponseEntity<ByteArray> {
        val userFileId = id.toTypeId<UserFileId>()
        val (record, data) =
            userFileDao.fetchDataOrNull(userFileId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        // security
        userSessionService.getUserSessionIfAuthenticated().let { session ->
            if (session == null || session.userId != record.userId) {
                return ResponseEntity(HttpStatus.UNAUTHORIZED)
            }
        }
        val headers = HttpHeaders()
        headers.contentType = MediaType.parseMediaType(record.contentType.type)
        return ResponseEntity.ok().headers(headers).body(data)
    }
}
