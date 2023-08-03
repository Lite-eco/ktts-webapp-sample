package templatesample

import templatesample.domain.ApplicationEnvironment
import templatesample.service.ApplicationInstance
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
@EnableJdbcHttpSession
class TemplateSampleApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("dev-env", ApplicationEnvironment.dev.name)
            System.setProperty(
                "logging.config", "classpath:logback-webapp-${ApplicationInstance.env}.xml")
            val app = SpringApplication(TemplateSampleApplication::class.java)
            app.setDefaultProperties(mapOf("spring.profiles.default" to springProfile()))
            app.run(*args)
        }

        fun springProfile(): String {
            if (ApplicationInstance.env == ApplicationEnvironment.test) {
                throw RuntimeException()
            }
            return ApplicationInstance.env.name.let { env ->
                if (ApplicationInstance.env == ApplicationEnvironment.dev) {
                    val userProfile = env + "-" + System.getProperty("user.name")
                    "$env,$userProfile"
                } else {
                    env
                }
            }
        }
    }
}
