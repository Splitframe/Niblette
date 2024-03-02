import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

project.description = "Niblette Frontend"
plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20"
//    id("io.github.turansky.seskar") version "1.17.0"
}

val frontendConfig: Map<String,String> = mapOf(
    "SERVER" to (System.getenv("SERVER") ?: "developer.local"),
    "SERVER_PORT" to (System.getenv("SERVER_PORT") ?: "8080"),
    "DEPLOYMENT" to (System.getenv("DEPLOYMENT") ?: "LOCAL"),
    "KEYCLOAK_SERVER" to (System.getenv("KEYCLOAK_SERVER") ?: "developer.local"),
    "KEYCLOAK_PORT" to (System.getenv("KEYCLOAK_PORT") ?: "8443"),
    "KEYCLOAK_REALM" to (System.getenv("KEYCLOAK_REALM") ?: ""),
    "KEYCLOAK_CLIENT" to (System.getenv("KEYCLOAK_CLIENT") ?: "frontend"),
)

kotlin {
    js("jsFrontend", IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                // In a split multiplatform project webpack fudges the
                // output file name, so we have to set it manually.
                outputFileName = "niblette-jsFrontend-js-frontend.js"
                cssSupport {
                    enabled.set(true)
                }
                export = false
            }
            frontendConfig.forEach { (key, value) ->
                webpackTask { args += listOf("--env", "$key=$value") }
                runTask { args += listOf("--env", "$key=$value") }
            }
        }
    }
    sourceSets {
        val jsFrontendMain by getting {
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")

            dependencies {
                implementation(project(":common"))

                bomImplementation(Libs.kotlinWrappers)

                implementation(Libs.kotlinCoroutinesJs)
                implementation(Libs.kotlinxSerializationJson)
                implementation(Libs.kotlinxDatetime)

                implementation(npm("axios", "0.21.1"))
                implementation(npm("react-intl", "6.5.2"))
                implementation(devNpm("process", "0.11.10"))
//                implementation(npm("dotenv", "16.3.1"))

                implementation(npm("date-fns", "2.29.3"))
                implementation(npm("@date-io/date-fns", "2.16.0"))
            }
        }
    }
}

tasks.getByName("jsFrontendProcessResources") {}

//tasks.patchWebpackConfig {
//    env("TEST_VALUE", "testValue")
//}

//val kotlinWrappersVersion = "1.0.0-pre.602"
//val ktorServerVersion = "2.2.4"
//val koinVersion = "3.4.0"
//val exposedVersion = "0.41.1"
//val flywayVersion = "9.16.1"
//val slf4jVersion = "2.0.6"
//
//
//fun kotlinWrappers(target: String): String = "org.jetbrains.kotlin-wrappers:kotlin-$target"
//fun ktor(target: String): String = "io.ktor:ktor-$target"
//fun exposed(target: String): String = "org.jetbrains.exposed:exposed-$target"
//fun koin(target: String): String = "io.insert-koin:koin-$target"
//fun flyway(target: String): String = "org.flywaydb:flyway-$target"
//fun slf4j(target: String): String = "org.slf4j:slf4j-$target"
//
//
//implementation(enforcedPlatform(kotlinWrappers("wrappers-bom:$kotlinWrappersVersion")))
//implementation(kotlinWrappers("react"))
//implementation(kotlinWrappers("react-dom"))
//implementation(kotlinWrappers("react-router-dom"))
//implementation(kotlinWrappers("mui"))
//implementation(kotlinWrappers("mui-icons"))
//implementation(kotlinWrappers("emotion"))
//
//implementation(npm("date-fns", "2.29.3"))
//implementation(npm("@date-io/date-fns", "2.16.0"))