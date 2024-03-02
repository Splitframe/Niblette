import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

project.description = "Niblette IRC"
plugins {

}

@Suppress("UNUSED_VARIABLE")
kotlin {
    jvm("irc") {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val ircMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                implementation(project(":common"))

                // BOM dependencies

                // Direct dependencies

            }
        }
    }
}

