import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

project.description = "Niblette Persistence"
plugins {

}

kotlin {
    jvm("persistence") {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val persistenceMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                implementation(project(":common"))

                // BOM dependencies
                bomImplementation(Libs.exposed)


                // Direct dependencies
                implementation(Libs.flywayCore)
                implementation(Libs.kotlinLogging)
            }
        }
    }
}

