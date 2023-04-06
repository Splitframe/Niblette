//import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("multiplatform") version "1.8.10"
    application
}


group = "de.niblette"
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
        fun kotlinWrappers(target: String): String =
            "org.jetbrains.kotlin-wrappers:kotlin-$target"
        fun ktor(target: String): String =
            "io.ktor:ktor-$target"
        fun exposed(target: String): String =
            "org.jetbrains.exposed:exposed-$target"
        fun koin(target: String): String =
            "io.insert-koin:koin-$target"
        fun flyway(target: String): String =
            "org.flywaydb:flyway-$target"
        fun slf4j(target: String): String =
            "org.slf4j:slf4j-$target"


        val kotlinWrappersVersion = "1.0.0-pre.529"
        val ktorServerVersion = "2.2.4"
        val koinVersion = "3.4.0"
        val exposedVersion = "0.41.1"
        val flywayVersion = "9.16.1"
        val slf4jVersion = "2.0.6"

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val backendMain by getting {
            repositories {
                flatDir {
                    dirs("lib")
                }
                mavenCentral()
            }

            dependencies {
                // ktor
                implementation(enforcedPlatform(ktor("bom:$ktorServerVersion")))
                implementation(ktor("server-netty"))
                implementation(ktor("server-core"))
                implementation(ktor("server-html-builder-jvm"))

                // exposed
                implementation(enforcedPlatform(exposed("bom:$exposedVersion")))
                implementation(exposed("core"))
                implementation(exposed("dao"))
                implementation(exposed("jdbc"))

                // koin
                //implementation(enforcedPlatform(koin("bom:$koinVersion")))
                implementation(koin("core:$koinVersion"))
                implementation(koin("ktor:$koinVersion"))

                // flyway
                //implementation(enforcedPlatform(flyway("bom:$flywayVersion")))
                implementation(flyway("core:$flywayVersion"))
                implementation(flyway("mysql:$flywayVersion"))

                // slf4j
                //implementation(enforcedPlatform(slf4j("bom:$flywayVersion")))
                implementation(slf4j("simple:$slf4jVersion"))
                implementation(slf4j("api:$slf4jVersion"))

                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
                implementation("ch.qos.logback:logback-core:1.4.5")
                implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")
                implementation("com.mysql:mysql-connector-j:8.0.32")
                implementation("org.pircbotx:pircbotx:2.3")
            }
        }
        val backendTest by getting
        val frontendMain by getting {
            dependencies {
                implementation(enforcedPlatform(kotlinWrappers("wrappers-bom:$kotlinWrappersVersion")))
                implementation(kotlinWrappers("react"))
                implementation(kotlinWrappers("react-dom"))
                implementation(kotlinWrappers("react-router-dom"))
                implementation(kotlinWrappers("mui"))
                implementation(kotlinWrappers("mui-icons"))
                implementation(kotlinWrappers("emotion"))

                implementation(npm("date-fns", "2.29.3"))
                implementation(npm("@date-io/date-fns", "2.16.0"))
            }
        }
        val frontendTest by getting
    }
}

application {
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
    mainClass.set("de.niblette.ServerKt")
}

tasks.withType<ShadowJar> {
    manifest {
        attributes(Pair("Main-Class","de.niblette.Serverkt.class"))
    }
    mergeServiceFiles {
        setPath("META-INF/services/org.flywaydb.core.extensibility.Plugin")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.0.2"
    }
}



//tasks.named<Copy>("backendProcessResources") {
//    val frontendBrowserDistribution = tasks.named("frontendBrowserDistribution")
//    from(frontendBrowserDistribution)
//}

//tasks.named<Copy>("copyIndex") {
//    from("src/main/doc")
//    into("build/target/doc")
//}

//tasks.named<JavaExec>("run") {
//    dependsOn(tasks.named<Jar>("backendJar"))
//    classpath(tasks.named<Jar>("backendJar"))
//}

//rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
//    versions.webpackCli.version = "4.10.0"
//}

//rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
//    rootProject.extensions.getByType<NodeJsRootExtension>(typeOf()).nodeVersion = "16.15.0"
//}