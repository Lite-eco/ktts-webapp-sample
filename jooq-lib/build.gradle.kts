import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "template-sample"

plugins { kotlin("jvm") }

val kotlinVersion = "1.7.21"

tasks {
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }
    register<JavaExec>("generateJooq") {
        main = "com.kttswebapptemplate.jooqlib.GenerateJooqAndDiffKt"
        classpath = sourceSets["main"].runtimeClasspath
    }
    register<JavaExec>("resetDatabase") {
        main = "com.kttswebapptemplate.jooqlib.ResetDatabaseKt"
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

    implementation("io.github.microutils:kotlin-logging:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.1")
    implementation("org.yaml:snakeyaml:1.32")

    implementation("org.postgresql:postgresql:42.5.0")
}
