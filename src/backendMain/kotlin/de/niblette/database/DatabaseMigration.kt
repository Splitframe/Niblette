package database

import org.flywaydb.core.Flyway

fun migrateDatabase(databaseConnection: DatabaseConnection) {
    Flyway
        .configure()
        .dataSource(
            "jdbc:mariadb://${databaseConnection.server}:${databaseConnection.port}",
            databaseConnection.user,
            databaseConnection.password
        )
        .defaultSchema("Niblette")
        .load()
        .migrate()
}