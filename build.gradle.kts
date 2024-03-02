plugins {
//    kotlin("multiplatform") version "1.9.22" apply false
}

allprojects {
//    group = "de.niblette"
    version = "2023.07.25"
}

subprojects {
    if (name != "jvmBackend") {
        apply(plugin = "kotlin-multiplatform")
        if (path.contains("jvmBackend")) {
            apply(plugin = "java")
        }
    }


    repositories {
        mavenLocal()
        mavenCentral()
    }
}

tasks.register("frontendRun") {
    val jsFrontendTask = subprojects
        .first { it.name == "jsFrontend" }
        .let { it.tasks.getByName("jsFrontendBrowserRun") }

    dependsOn(tasks.getByName("kotlinUpgradeYarnLock"))
    dependsOn(jsFrontendTask)
}