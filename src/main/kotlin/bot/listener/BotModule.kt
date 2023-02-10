package bot.listener

import database.show.ShowRepository
import org.koin.dsl.module
import org.pircbotx.Configuration
import org.pircbotx.PircBotX

fun botModule(botConfig: Configuration) = module {
    single<PircBotX>() {
        PircBotX(botConfig)
    }
}