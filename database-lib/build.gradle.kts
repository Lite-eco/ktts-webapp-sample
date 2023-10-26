import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { kotlin("jvm") }

val kotlinVersion = "1.9.0"
val jooqVersion = "3.18.4"

tasks {
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }
    register<JavaExec>("generateJooq") {
        mainClass.set("com.kttswebapptemplate.database.GenerateJooqAndDiffKt")
        classpath = sourceSets["main"].runtimeClasspath
    }
    register<JavaExec>("resetDatabase") {
        mainClass.set("com.kttswebapptemplate.database.ResetDatabaseKt")
        classpath = sourceSets["main"].runtimeClasspath
    }
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

sourceSets.getByName("main") { java.srcDir("src/generated/kotlin") }

repositories {
    mavenCentral()
    mavenLocal()
}

configurations.all { exclude("junit") }

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    // database
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.jooq:jooq:$jooqVersion")
    implementation("org.jooq:jooq-meta:$jooqVersion")
    implementation("org.jooq:jooq-codegen:$jooqVersion")

    // logs
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // utils
    implementation("com.github.jsqlparser:jsqlparser:4.6")
    implementation("org.yaml:snakeyaml:2.0")

    // tests
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}
