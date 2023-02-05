import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.8.10"
    application
}

group = "de.niblette"
//version = "1.0-SNAPSHOT"

repositories {
    flatDir {
        dirs("lib")
    }
    mavenCentral()
}

//val exposedVersion: String by project
//val koinVersion: String by project
val exposedVersion = "0.40.1"
val koinVersion = "3.3.2"

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")
    implementation("org.flywaydb:flyway-core:9.12.0")
    implementation("org.flywaydb:flyway-mysql:9.12.0")
    implementation("com.mysql:mysql-connector-j:8.0.32")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("ch.qos.logback:logback-core:1.4.5")
    implementation("org.pircbotx:pircbotx:2.3")
//    implementation(files("/lib/pircbotx/pircbotx.jar"))
}

tasks.withType<ShadowJar> {
    manifest {
        attributes(Pair("Main-Class","de.niblette.Mainkt.class"))
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "19"
}

application {
    mainClass.set("MainKt")
}



