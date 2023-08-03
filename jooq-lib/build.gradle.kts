import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "template-sample"

plugins { kotlin("jvm") version "1.6.20" }

val kotlinVersion = "1.6.20"

kotlin {
    sourceSets.all {
        languageSettings.apply {
            languageVersion = "1.6"
            apiVersion = "1.6"
            progressiveMode = true
        }
    }
}

tasks {
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "15" }
    register<JavaExec>("generateJooq") {
        main = "templatesample.jooqlib.GenerateJooqAndDiffKt"
        classpath = sourceSets["main"].runtimeClasspath
    }
    register<JavaExec>("resetDatabase") {
        main = "templatesample.jooqlib.ResetDatabaseKt"
        classpath = sourceSets["main"].runtimeClasspath
    }
}

sourceSets.getByName("main") { java.srcDir("src/generated/java") }

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    api("jooq-utils:jooq-utils:0.0.6-SNAPSHOT")

    implementation("io.github.microutils:kotlin-logging:1.4.6")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.yaml:snakeyaml:1.27")

    implementation("org.postgresql:postgresql:42.2.18")
}
