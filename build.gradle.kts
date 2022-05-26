import ru.mipt.npm.gradle.KScienceVersions
import java.time.LocalDateTime

plugins {
    id("ru.mipt.npm.gradle.project")
    id("ru.mipt.npm.gradle.jvm")
    application
}

repositories {
    mavenLocal()
}

group = "ru.mipt.npm"
version = "0.1.0-SNAPSHOT"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment", "-Xmx200M")
}


val dataforgeVersion by extra("0.6.0-dev-9")
val ktorVersion = KScienceVersions.ktorVersion

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.5")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("space.kscience:dataforge-workspace:$dataforgeVersion")
    implementation("space.kscience:dataforge-io-yaml:$dataforgeVersion")
    implementation("org.jetbrains:markdown:0.3.1")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Disabled
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

sourceSets {
    main {
        resources.srcDir(project.rootDir.resolve("data"))
    }
}

val writeBuildDate: Task by tasks.creating {
    doFirst {
        val deployDate = LocalDateTime.now()
        project.buildDir.resolve("resources/main/buildDate").writeText(deployDate.toString())
    }
    outputs.file("resources/main/buildDate")
}


//write build time in build to check outdated external data directory
tasks.getByName("processResources").dependsOn(writeBuildDate)