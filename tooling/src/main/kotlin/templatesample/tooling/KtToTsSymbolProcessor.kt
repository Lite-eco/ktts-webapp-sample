package templatesample.tooling

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.validate
import java.nio.file.Files
import java.time.LocalDateTime
import kotlin.io.path.absolutePathString
import kttots.Shared
import templatesample.tooling.kttots.ClassMapper
import templatesample.tooling.kttots.ClassParser
import templatesample.tooling.kttots.ClassWriter
import templatesample.tooling.kttots.Debug
import templatesample.tooling.kttots.ImportWriter.fileToPath
import templatesample.tooling.kttots.ImportWriter.relativePath
import templatesample.tooling.kttots.KtToTsConfiguration
import templatesample.tooling.kttots.ShellRunner
import templatesample.tooling.kttots.prettyPrint

// TODO[tmpl] use exceptions and catch them for debug report ?
// TODO[tmpl] clean !!
// TODO[tmpl] in TS empty interfaces are useless
// TODO[tmpl]
// https://docs.gradle.org/current/userguide/custom_plugins.html#sec:writing_tests_for_your_plugin
// TODO[tmpl] see ExtensionContainer to extend the plugin
// TODO[tmpl] problem if an objectType in CommandResponse... should be smarter
// support Jackson annotations
class KtToTsSymbolProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger,
    val options: Map<String, String>
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val millis = System.currentTimeMillis()
        val configuration = KtToTsConfiguration.init(options)
        val debugReport = if (configuration.debugFile != null) StringBuilder() else null
        debugReport?.appendLine("<html><body><pre>")
        debugReport?.appendLine("Start generation ${LocalDateTime.now()}")
        debugReport?.appendLine("<h1>Configuration</h1>")
        debugReport?.appendLine(configuration.prettyPrint())
        val symbols =
            resolver
                .getSymbolsWithAnnotation(Shared::class.java.name)
                .filterIsInstance<KSClassDeclaration>()
        // TODO[tmpl] what happens if no file ?
        if (!symbols.iterator().hasNext()) return emptyList()
        debugReport?.apply {
            appendLine("<h1>Initial symbols selection</h1>")
            symbols.forEach { appendLine("$it") }
        }
        //        val visitor = KtToTsVisitor()
        // TODO[tmpl] add exceptions : mapped classes in configuration
        //        val parsingResult =
        //            symbols.fold(emptySet<ClassParser.Parsed>()) { acc, declaration ->
        //                declaration.accept(visitor, acc)
        //            }
        val parsingResult =
            symbols.fold(emptySet<ClassParser.Parsed>()) { acc, declaration ->
                ClassParser.parse(declaration.asStarProjectedType(), acc)
            }
        debugReport?.apply {
            appendLine("<h1>Class list (${parsingResult.size} items)</h1>")
            parsingResult.forEach { appendLine(it.type.declaration.simpleName.asString()) }
        }
        val filesSelection = parsingResult.mapNotNull { it.type.declaration.containingFile }.toSet()
        debugReport?.apply {
            appendLine("<h1>Files list (${filesSelection.size} items)</h1>")
            filesSelection.forEach { appendLine(it.filePath) }
        }
        //        val typesSelection = parsingResult.map { it.declaration }
        //        val importsMap = parsingResult.associateBy { it.declaration }
        val parsingResultMap = parsingResult.associateBy { it.type.declaration }
        val resultFiles =
            filesSelection
                .map { ksFile ->
                    val fileDeclarations =
                        ksFile.declarations.toList().flatMap { d ->
                            val innerDeclarations =
                                if (d is KSClassDeclaration) {
                                    d.declarations.toList()
                                } else emptyList()
                            listOf(d) + innerDeclarations
                        }
                    ksFile to fileDeclarations.mapNotNull { parsingResultMap.get(it) }
                }
                .map { (ksFile, parsed) ->
                    val file = configuration.generatedDirectory.resolve(clientFile(ksFile))
                    //                debugReport?.appendLine("$file")
                    file.parent.toFile().mkdirs()
                    // TODO un imports writer...
                    val imports = let {
                        val dependenciesImportsMapped =
                            parsed.flatMap {
                                (it.type.declaration as KSClassDeclaration)
                                    .declarations
                                    .filterIsInstance<KSPropertyDeclaration>()
                                    .mapNotNull { ClassMapper.mapProperty(it.type) }
                            }
                        val dependenciesImports =
                            parsed
                                .flatMap { it.dependencies }
                                .toSet()
                                .mapNotNull { t -> t.resolve().declaration as? KSClassDeclaration }
                                .mapNotNull { d ->
                                    d.containingFile?.let {
                                        ClassMapper.ClassMapping(
                                            ClassWriter.className(d), fileToPath(it, configuration))
                                    }
                                }
                        val classImports =
                            parsed
                                .mapNotNull { it.type.declaration as? KSClassDeclaration }
                                .mapNotNull { ClassMapper.mapClass(it) }
                        dependenciesImportsMapped + dependenciesImports + classImports
                    }
                    val sb = StringBuilder()
                    val filePath = fileToPath(ksFile, configuration)
                    imports
                        .groupBy { it.packagePath }
                        .toList()
                        .mapNotNull { p -> p.first?.let { it to p.second } }
                        .filter { it.first != filePath }
                        .sortedBy { it.first }
                        .forEach { (file, imports) ->
                            val i =
                                imports
                                    .map {
                                        val i = it.name.indexOf("<")
                                        if (i != -1) {
                                            it.name.substring(0, i)
                                        } else {
                                            it.name
                                        }
                                    }
                                    .distinct()
                                    .sorted()
                                    .joinToString(separator = ", ")
                            val from = relativePath(file, filePath, configuration)
                            sb.appendLine("import { $i } from '$from';")
                        }
                    sb.appendLine("")
                    //                val keepDeclarations = parsed.map { it.type.declaration }
                    // [doc] restarting from file here (instead of using directly parsed) permits
                    // order conservation
                    parsed.forEach { sb.append(ClassWriter.toTs(it)) }
                    Files.write(file, sb.toString().toByteArray())
                    file
                }
        //        debugReport?.let {
        //            typesSelection.map { debugReport.appendLine("${it.qualifiedName?.asString()}")
        // }
        //        }
        debugReport?.appendLine("<h1>Report</h1>")
        debugReport?.appendLine("Finished generation ${LocalDateTime.now()}")
        debugReport?.appendLine("Took ${System.currentTimeMillis() - millis}ms")
        debugReport?.appendLine("<h1>Debug autre</h1>")
        debugReport?.appendLine(Debug.sb.toString())
        debugReport?.appendLine("</pre></body></html>")
        debugReport?.let {
            if (configuration.debugFile == null) {
                throw RuntimeException()
            }
            configuration.debugFile.parentFile.mkdirs()
            configuration.debugFile.writeText(debugReport.toString())
        }
        val unableToProcess = symbols.filterNot { it.validate() }.toList()
        resultFiles.forEach {
            ShellRunner.run(
                configuration.clientDirectory,
                "node_modules/prettier/bin-prettier.js",
                "--config",
                "package.json",
                "--write",
                it.absolutePathString())
        }
        return unableToProcess
    }

    fun clientFile(ksFile: KSFile) =
        ksFile.filePath
            .split("/")
            .takeLast(ksFile.packageName.asString().split(".").size)
            .fold("") { acc, s -> "$acc/$s" }
            .let { it.substring(1, it.lastIndexOf(".")) + ".ts" }
}
