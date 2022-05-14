import ru.mipt.npm.gradle.KScienceVersions

plugins {
    id("ru.mipt.npm.gradle.project")
    id("ru.mipt.npm.gradle.jvm")
    application
}

repositories{
    mavenLocal()
}

group = "ru.mipt.npm"
version = "0.0.1-SNAPSHOT"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment", "-Xmx200M")
}


val dataforgeVersion by extra("0.6.0-dev-7")
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
    sourceSets.all {
        languageSettings {
            languageVersion  = "1.7"
            apiVersion = "1.7"
        }
    }
}

sourceSets {
    main {
        resources.srcDir(project.rootDir.resolve("data"))
    }
}