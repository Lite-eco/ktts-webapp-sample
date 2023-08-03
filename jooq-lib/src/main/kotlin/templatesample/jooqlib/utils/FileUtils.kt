package templatesample.jooqlib.utils

import java.nio.file.Path
import java.nio.file.Paths

object FileUtils {
    fun projectBasePath(): Path {
        val userDir = System.getProperty("user.dir")
        val dir = let {
            listOf(
                    // happens in template-sample-server tests
                    "/template-sample-server",
                )
                .forEach {
                    if (userDir.endsWith(it)) {
                        return@let userDir.dropLast(it.length)
                    }
                }
            userDir
        }
        return Paths.get(dir)
    }
}
