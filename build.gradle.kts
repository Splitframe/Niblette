import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform") version "1.8.10"

    application
}


group = "de.splitframe"
version = "1.0-SNAPSHOT"

//val devMode = properties.containsKey("jsWatch")

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm("backend") {
        jvmToolchain(16)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js("frontend", IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val backendMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:2.0.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("org.slf4j:slf4j-api:2.0.6")
                implementation("org.slf4j:slf4j-simple:2.0.6")
                implementation("ch.qos.logback:logback-core:1.4.5")
            }
        }
        val backendTest by getting
        val frontendMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.346")
            }
        }
        val frontendTest by getting
    }
}

application {
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
    mainClass.set("de.splitframe.application.ServerKt")
}

tasks.named<Copy>("backendProcessResources") {
    val frontendBrowserDistribution = tasks.named("frontendBrowserDistribution")
    from(frontendBrowserDistribution)
}

//tasks.named<Copy>("copyIndex") {
//    from("src/main/doc")
//    into("build/target/doc")
//}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("backendJar"))
    classpath(tasks.named<Jar>("backendJar"))
}

//rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
//    versions.webpackCli.version = "4.10.0"
//}

//rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
//    rootProject.extensions.getByType<NodeJsRootExtension>(typeOf()).nodeVersion = "16.15.0"
//}