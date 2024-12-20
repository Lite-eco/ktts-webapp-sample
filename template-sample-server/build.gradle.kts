plugins {
    kotlin("jvm")
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springframework.boot") version "3.2.1"
    // so we don't need to open Spring components classes
    id("org.jetbrains.kotlin.plugin.spring")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

val kotlinVersion = "1.9.22"

tasks {
    test {
        useJUnitPlatform()
        addTestListener(
            object : TestListener {
                override fun beforeSuite(suite: TestDescriptor) {}

                override fun beforeTest(testDescriptor: TestDescriptor) {}

                override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}

                override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                    if (suite.parent == null) {
                        println("\n | Test result: ${result.resultType}")
                        println(
                            " | Test summary: ${result.testCount} tests, " +
                                "${result.successfulTestCount} succeeded, " +
                                "${result.failedTestCount} failed, " +
                                "${result.skippedTestCount} skipped\n")
                    }
                }
            })
    }
}

springBoot { mainClass.set("com.kttswebapptemplate.TemplateSampleApplicationKt") }

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

configurations.all { exclude(group = "junit", module = "junit") }

ksp {
    arg("kt2ts:clientDirectory", "$rootDir/template-sample-client")
    arg("kt2ts:dropPackage", "com.kttswebapptemplate")
    arg("kt2ts:mappings", "$rootDir/template-sample-client/kt-to-ts-mappings.json")
    arg(
        "kt2ts:nominalStringMappings",
        listOf(
                "com.kttswebapptemplate.domain.SerializeAsString",
                "com.kttswebapptemplate.domain.TemplateSampleId",
                "com.kttswebapptemplate.domain.TemplateSampleSecurityString")
            .joinToString(separator = "|"))
    arg("kt2ts:nominalStringImport", "utils/nominal-class.ts")
    arg("kt2ts:absoluteImport", "true")
    arg("kt2ts:debugFile", "$rootDir/template-sample-client/build/debug-generation.html")
}

dependencies {
    implementation(project(":database-lib"))

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.session:spring-session-jdbc")

    // kt2ts
    implementation("io.github.kt2ts:kt2ts-annotation:1.0.0")
    ksp("io.github.kt2ts:kt2ts-ksp-generator:0.0.4")

    // database
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.jooq:jooq:3.19.1")

    // logs
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // utils
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("org.apache.commons:commons-text:1.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    implementation("org.reflections:reflections") {
        // [doc] BEWARE reflections version 0.10.2 breaks id deserialization when deployed
        version { strictly("0.9.12") }
    }
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.json:json:20231013")

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.19.8")
    testImplementation("org.testcontainers:jdbc:1.19.3")
    testImplementation("org.testcontainers:testcontainers:1.19.3")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}
