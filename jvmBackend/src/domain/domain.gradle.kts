project.description = "Niblette core"
plugins {

}

kotlin {
    jvm("domain") {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val domainMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                implementation(project(":common"))
            }
        }
    }
}

