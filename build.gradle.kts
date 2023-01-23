import space.kscience.gradle.KScienceVersions
import space.kscience.snark.plugin.JSch
import space.kscience.snark.plugin.execute
import space.kscience.snark.plugin.uploadDirectory
import space.kscience.snark.plugin.useSession

plugins {
    id("space.kscience.gradle.project")
    id("space.kscience.gradle.jvm")
    id("space.kscience.snark")
    application
}

group = "center.sciprog"
version = "0.1.0"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment", "-Xmx200M")
}

val snarkVersion: String by extra
val ktorVersion = KScienceVersions.ktorVersion

dependencies {
    implementation("space.kscience:snark-ktor:$snarkVersion")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-http-redirect:$ktorVersion")
    implementation("io.ktor:ktor-server-forwarded-header:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Disabled
}

apiValidation{
    validationDisabled = true
}

/* Upload with JSch */

val host = System.getenv("SPC_HOST")
val user = System.getenv("SPC_USER")
val identityString = System.getenv("SPC_ID")
val serviceName = "sciprog-site"

if (host != null && user != null || identityString != null) {
    val uploadDistribution by tasks.creating {
        group = "distribution"
        dependsOn("installDist")
        doLast {
            JSch {
                addIdentity("spc-webmaster", identityString.encodeToByteArray(), null, null)
            }.useSession(host, user) {
                //stopping service during the upload
                execute("sudo systemctl stop $serviceName")
                uploadDirectory(buildDir.resolve("install/spc-site"), "/opt")
                //adding executable flag to the entry point
                execute("sudo chmod +x /opt/spc-site/bin/spc-site")
                execute("sudo systemctl start $serviceName")
            }
        }
    }

    val reloadDistribution by tasks.creating {
        group = "distribution"
        doLast {
            JSch {
                addIdentity("spc-webmaster", identityString.encodeToByteArray(), null, null)
            }.useSession(host, user) {
                execute("sudo systemctl restart $serviceName")
            }
        }
    }

} else {
    logger.error("Host, user or ID are not defined. Skipping deployment tasks.")
}