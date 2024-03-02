import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

project.description = "Niblette API"
plugins {

}

@Suppress("UNUSED_VARIABLE")
kotlin {
    jvm("api") {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val apiMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                implementation(project(":common"))

                // BOM dependencies


                // Direct dependencies

            }
        }
    }
}

