package templatesample.service.utils

import javax.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.stereotype.Service
import templatesample.repository.log.DeploymentLogDao
import templatesample.service.ApplicationInstance
import templatesample.service.DateService

@Service
class PreDestroySchedulerService(
    private val deploymentLogDao: DeploymentLogDao,
    private val dateService: DateService
) {

    private val logger = KotlinLogging.logger {}

    @PreDestroy
    fun preDestroy() {
        logger.info { "Process predestroy tasks" }
        deploymentLogDao.updateShutdownTime(ApplicationInstance.deploymentLogId, dateService.now())
        logger.info { "Predestroy OK" }
    }
}
