import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

project.description = "Ivanto Portal Commons"


plugins {
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    js("js", IR) {
        browser()
    }
    jvm("jvm") {
        jvmToolchain(17)
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                bomImplementation(Libs.kotlinCoroutinesCommon)

                implementation(Libs.kotlinxDatetime)
                implementation(Libs.kotlinxSerializationCore)
                implementation(Libs.kotlinxSerializationJson)

                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:1.9.0")
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
//                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            }
        }

        val jsMain by getting {
            dependencies {

            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Libs.kotlinLogging)
            }
        }
//        val commonTest by getting

        val jsTest by getting

        val jvmTest by getting
    }
}