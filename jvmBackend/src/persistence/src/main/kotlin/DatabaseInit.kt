package de.niblette.persitence
// Whole thing stolen from ticketing server.

import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import javax.sql.DataSource

private val logger = KotlinLogging.logger { }

/**
 * Initialize and migrate database using Flyway.
 *
 * Attention: This method must be run only once!
 */
fun initDatabase(dataSource: DataSource): Database {
    // Exposed database connection
    logger.info("Connecting to database")
    val database = Database.connect(dataSource)
    // Flyway migration
    val flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations("db/migration/postgresql")
        .ignoreMigrationPatterns("*:ignored") // needed for hotfix migrations

        // the following 3 lines are for "resetting" the migrations to a new baseline.
        // New and empty databases will use the new baseline migration script for building up all schemas.
        // Old and existing databases will ignore the new baseline migration script as they already have the latest schemas.
        .table("flyway_schema_history_2")
        .baselineVersion("1")
        .baselineOnMigrate(true)
        .cleanDisabled(true) // We don't want to accidentally wipe our prod db.
//        .placeholders(mapOf("environment_prefix" to environmentPrefix.toString()))
        .load()

    logger.info("Start database migration")
    flyway.migrate()
    logger.info("Database migration finished")
    return database
}

fun <T> Database.transaction(statement: Transaction.() -> T) =
    org.jetbrains.exposed.sql.transactions.transaction(this, statement)