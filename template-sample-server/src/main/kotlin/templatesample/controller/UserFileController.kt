package templatesample.controller

import templatesample.domain.UserFileId
import templatesample.repository.user.UserFileDao
import templatesample.utils.TemplateSampleStringUtils
import templatesample.utils.toTypeId
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class UserFileController(private val userFileDao: UserFileDao) {

    @GetMapping("/user-file/{id}")
    fun getUserFile(@PathVariable id: String): ResponseEntity<ByteArray> {
        // TODO[tmpl][secu]
        val userFileId = TemplateSampleStringUtils.deserializeUuid(id).toTypeId<UserFileId>()
        val userFile =
            userFileDao.fetchDataOrNull(userFileId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val headers = HttpHeaders()
        headers.contentType = MediaType.parseMediaType(userFile.contentType)
        return ResponseEntity.ok().headers(headers).body(userFile.file)
    }
}
