import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "me.hilde"
version = "1.0-SNAPSHOT"

repositories {
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



