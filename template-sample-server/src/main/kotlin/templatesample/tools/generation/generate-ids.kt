package templatesample.tools.generation

import java.nio.file.Files
import java.nio.file.Paths
import org.reflections.Reflections
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import templatesample.domain.TemplateSampleId
import templatesample.serialization.Serializer.idsPackage

fun main() {
    val stringBuilder = StringBuilder()
    val reflections =
        Reflections(
            ConfigurationBuilder()
                .filterInputsBy(FilterBuilder().includePackage(idsPackage))
                .setUrls(ClasspathHelper.forPackage(idsPackage)))
    val idClasses: List<Class<out TemplateSampleId<*>>> =
        reflections.getSubTypesOf(TemplateSampleId::class.java).sortedBy { it.simpleName }
    stringBuilder.appendLine("import { NominalString } from '../utils/nominal-class';")
    stringBuilder.appendLine()
    stringBuilder.appendLine("export type TemplateSampleId =")
    idClasses.forEach { stringBuilder.appendLine("  | ${it.simpleName}") }
    stringBuilder.appendLine()
    idClasses.forEach {
        stringBuilder.appendLine(
            "export type ${it.simpleName} = NominalString<'${it.simpleName}'>;")
    }
    val path = System.getProperty("user.dir") + "/template-sample-client/src/domain/ids.ts"
    Files.write(Paths.get(path), stringBuilder.toString().toByteArray(Charsets.UTF_8))
}
