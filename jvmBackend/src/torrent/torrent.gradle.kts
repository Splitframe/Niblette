import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

project.description = "Niblette Torrent"
plugins {

}

kotlin {
    jvm("torrent") {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val torrentMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                implementation(project(":common"))

                // BOM dependencies

                // Direct dependencies

            }
        }
    }
}

