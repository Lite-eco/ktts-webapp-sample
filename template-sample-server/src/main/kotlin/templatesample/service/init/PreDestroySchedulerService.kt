package templatesample.service.init

import javax.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.stereotype.Service
import templatesample.repository.log.DeploymentLogDao
import templatesample.service.utils.ApplicationInstance
import templatesample.service.utils.DateService

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
