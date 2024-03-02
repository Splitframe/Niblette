rootProject.name = "Niblette"

val rootProjects = listOf("common", "jsFrontend", "jvmBackend")

// Multiplatform roots
rootProjects.forEach {
    include(":$it")
}

// Server Submodules
include(":boot")
include(":domain")
include(":api")
include(":persistence")
include(":irc")
include(":torrent")

rootProject.children.forEach { project ->
    project.buildFileName = "${project.name}.gradle.kts"

    if (project.name !in rootProjects) {
        project.projectDir = File("jvmBackend/src/${project.name}")
    }
}