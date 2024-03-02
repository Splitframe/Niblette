package de.niblette.modules.persistence

data class DatabaseConfig(
    val server: String,
    val port: String,
    val driver: String = "org.postgresql.Driver",
    val user: String,
    val password: String,
    val url: String = "jdbc:postgresql://$server:$port/niblette"
)