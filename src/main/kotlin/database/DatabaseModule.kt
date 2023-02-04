package database

import database.show.ShowRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

fun databaseModule(databaseConnection: DatabaseConnection) = module {
    single(createdAtStart = true) {
        Database.connect(
            url = "jdbc:mariadb://${databaseConnection.server}:${databaseConnection.port}/Niblette",
            driver = databaseConnection.driver,
            user = databaseConnection.user,
            password = databaseConnection.password
        )
    }
}