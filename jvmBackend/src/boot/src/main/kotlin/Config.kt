package de.niblette.boot

import de.niblette.modules.irc.IrcConfig
import de.niblette.modules.persistence.DatabaseConfig

data class Config (
    val database: DatabaseConfig,
    val ircConfig: List<IrcConfig>,
)