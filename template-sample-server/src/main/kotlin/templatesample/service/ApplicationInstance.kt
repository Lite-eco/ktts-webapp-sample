package templatesample.service

import com.google.common.base.Charsets
import com.google.common.base.Strings
import com.google.common.io.Files
import templatesample.domain.ApplicationEnvironment
import templatesample.domain.DeploymentLogId
import templatesample.repository.log.DeploymentLogDao
import java.io.File
import java.io.FileInputStream
import java.util.Properties
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class ApplicationInstance(
    val environment: Environment,
    val deploymentLogDao: DeploymentLogDao,
    val randomService: RandomService,
    val dateService: DateService
) {

    companion object {
        // is reset by TemplateSampleApplication when env is different
        var env = ApplicationEnvironment.test
    }

    init {
        // verify env is in profiles
        val profiles = let {
            val e = ApplicationEnvironment.values().map { it.name }
            environment.activeProfiles.filter { it in e }
        }
        // if not empty, let's check profiles are consistent with env
        // (if is empty, default profiles will be enabled)
        if (profiles.isNotEmpty()) {
            if (profiles.first() != env.name) {
                throw IllegalStateException("Spring profiles should start by $env (is $profiles)")
            }
            if (profiles.size != 1) {
                throw IllegalStateException(
                    "Spring profiles list contains multiple environments : $profiles")
            }
        }
    }

    val gitRevisionProperties by lazy {
        File(System.getProperty("user.dir") + "/build.properties").let { file ->
            if (file.exists()) {
                Properties().apply { load(FileInputStream(file)) }
            } else {
                null
            }
        }
    }

    val gitRevisionLabel: String by lazy {
        val buildDiffFile = File(System.getProperty("user.dir") + "/git.diff")
        val notNullGitRevisionProperties = gitRevisionProperties
        if (notNullGitRevisionProperties != null && buildDiffFile.exists()) {
            val buildDiff = Files.asCharSource(buildDiffFile, Charsets.UTF_8).readFirstLine()
            notNullGitRevisionProperties.getProperty("shortGitRevision") +
                (if (!Strings.isNullOrEmpty(buildDiff)) " + DIFF" else "")
        } else {
            "[dev]"
        }
    }

    // [doc] deploymentId insertion is lazy in development, which permits a database cleaning during
    // the application launch
    val deploymentId by lazy {
        val id = randomService.id<DeploymentLogId>()
        deploymentLogDao.insert(
            DeploymentLogDao.Record(
                id,
                gitRevisionLabel,
                dateService.serverZoneId(),
                dateService.now(),
                shutdownDate = null))
        id
    }
}
