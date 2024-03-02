import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

project.description = "Niblette Entrypoint"

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
    java
}

kotlin {
    jvm("boot") {
        jvmToolchain(21)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        @SuppressWarnings("unused")
        val bootMain by getting {
            kotlin.srcDir("src/main/kotlin")
            dependencies {
                implementation(project(":common"))
                implementation(project(":domain"))
                implementation(project(":api"))
                implementation(project(":persistence"))
                implementation(project(":irc"))
                implementation(project(":torrent"))

                // BOM dependencies
                bomImplementation(Libs.exposed, listOf("exposed-core", "exposed-dao"))
                bomImplementation(Libs.kotlinCoroutinesJvm)
                bomImplementation(Libs.ktor)

                // Direct dependencies
                implementation(Libs.hikari)
                implementation(Libs.slf4j)
                implementation(Libs.kotlinLogging)
                implementation(Libs.postgres)
//                implementation("io.ktor:ktor-server-netty:2.2.4")
//                implementation("io.ktor:ktor-server-html-builder-jvm:2.2.4")
//                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
    }
}

//tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//}

val serverClass = "de.niblette.boot.ApplicationKt"

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("bootJar"))
    classpath(tasks.named<Jar>("bootJar"))
}

tasks.withType<ShadowJar> {
    archiveFileName = "niblette-server.jar"
    manifest {
        attributes(Pair("Main-Class",serverClass))
    }
//    mergeServiceFiles {
//        setPath("META-INF/services/org.flywaydb.core.extensibility.Plugin")
//    }
}

application {
    mainClass.set(serverClass)
}

