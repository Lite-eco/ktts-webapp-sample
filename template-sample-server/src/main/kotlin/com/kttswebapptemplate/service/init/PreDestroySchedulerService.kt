package com.kttswebapptemplate.service.init

import com.kttswebapptemplate.repository.log.DeploymentLogDao
import com.kttswebapptemplate.service.utils.ApplicationInstance
import com.kttswebapptemplate.service.utils.DateService
import javax.annotation.PreDestroy
import mu.KotlinLogging
import org.springframework.stereotype.Service

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
