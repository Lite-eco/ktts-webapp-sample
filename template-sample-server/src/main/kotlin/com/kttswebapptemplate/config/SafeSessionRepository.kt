package com.kttswebapptemplate.config

import com.kttswebapptemplate.domain.ApplicationEnvironment
import com.kttswebapptemplate.service.utils.ApplicationInstance
import mu.KotlinLogging
import org.springframework.session.FindByIndexNameSessionRepository
import org.springframework.session.Session

// [doc] from
// https://sdqali.in/blog/2016/11/02/handling-deserialization-errors-in-spring-redis-sessions/
// and https://github.com/spring-projects/spring-session/issues/280
// updated for spring boot 2...
class SafeSessionRepository(private val repository: FindByIndexNameSessionRepository<Session>) :
    FindByIndexNameSessionRepository<Session> by repository {

    private val logger = KotlinLogging.logger {}

    override fun findById(id: String): Session? =
        try {
            repository.findById(id)
        } catch (e: Exception) {
            if (ApplicationInstance.env == ApplicationEnvironment.Dev) {
                throw IllegalArgumentException(
                    "[FIX BEFORE GO TO PROD] Session code update : deserialization problem", e)
            } else {
                logger.error { "Deleting session $id" }
                deleteById(id)
            }
            null
        }

    override fun save(session: Session) {
        try {
            repository.save(session)
        } catch (e: Exception) {
            if (ApplicationInstance.env == ApplicationEnvironment.Dev) {
                throw IllegalArgumentException(
                    "[FIX BEFORE GO TO PROD] Session code update : serialization problem", e)
            } else {
                logger.error { "Deleting session ${session.id}" }
                deleteById(session.id)
            }
        }
    }
}
