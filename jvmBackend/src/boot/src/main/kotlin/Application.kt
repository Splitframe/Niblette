package de.niblette.boot

import de.niblette.boot.api.ApiModule
import de.niblette.boot.api.ApiModuleImpl
import de.niblette.modules.irc.IrcConfig
import de.niblette.modules.persistence.DatabaseConfig
import de.niblette.common.Application
import de.niblette.modules.persistence.DatabaseModule
import de.niblette.modules.persistence.DatabaseModuleImpl


fun main() {

    val dbServer    = System.getenv("DB_SERVER")
    val dbPort      = System.getenv("DB_PORT")
    val dbDriver    = System.getenv("DB_DRIVER")
    val dbUser      = System.getenv("DB_USER")
    val dbPassword  = System.getenv("DB_PASSWORD")

    val databaseConfig = DatabaseConfig(
        server   = dbServer,
        port     = dbPort,
        user     = dbUser,
        password = dbPassword,
    )

    val config = Config(
        database = databaseConfig,
        ircConfig = listOf()
    )

    val application = NibletteApplication(config)
    application.start()
}

class NibletteApplication(
    private val config: Config
) : Application() {
    private val apiModule: ApiModule = module { ApiModuleImpl(config) }
    private val databaseModule: DatabaseModule = module { DatabaseModuleImpl(config.database) }
}