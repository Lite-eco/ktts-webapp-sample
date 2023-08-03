package templatesample.service.utils

import templatesample.repository.log.DeploymentLogDao
import templatesample.service.ApplicationInstance
import templatesample.service.DateService
import javax.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class PreDestroySchedulerService(
    val deploymentLogDao: DeploymentLogDao,
    val dateService: DateService
) {

    private val logger = KotlinLogging.logger {}

    @PreDestroy
    fun preDestroy() {
        logger.info { "Process predestroy tasks" }
        deploymentLogDao.updateShutdownTime(ApplicationInstance.deploymentLogId, dateService.now())
        logger.info { "Predestroy OK" }
    }
}
