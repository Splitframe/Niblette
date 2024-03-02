package de.niblette.modules.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import de.niblette.persitence.initDatabase
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs
import kotlin.time.Duration.Companion.minutes
import de.niblette.common.Module

interface DatabaseModule : Module {
    val database: Database
    val sqlLogger: SqlLogger
}

class DatabaseModuleImpl(
    private val databaseConfig: DatabaseConfig,
) : DatabaseModule {

    private val dataSource = HikariConfig().run {
        jdbcUrl = databaseConfig.url
        username = databaseConfig.user
        password = databaseConfig.password
        driverClassName = databaseConfig.driver
        // see https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
        // for an in-depth explanation about how to figure the pool size
        maximumPoolSize = 8
        maxLifetime = 15.minutes.inWholeMilliseconds

        addDataSourceProperty("reWriteBatchedInserts", "true") // improves batch insert performance
        HikariDataSource(this)
    }

    private object KotlinLoggingSqlLogger : SqlLogger {
        private val logger = KotlinLogging.logger { }

        override
        fun log(context: StatementContext, transaction: Transaction) {
            logger.info { "SQL: ${context.expandArgs(transaction)}" }
        }
    }

    override val database: Database = initDatabase(
        dataSource = dataSource,
    )

    override val sqlLogger: SqlLogger = KotlinLoggingSqlLogger
}