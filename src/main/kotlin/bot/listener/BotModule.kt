package bot.listener

import database.show.ShowRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.koin.dsl.module
import org.pircbotx.Configuration
import org.pircbotx.PircBotX

fun botModule(botConfig: Configuration) = module {
    single<PircBotX>(createdAtStart = true) {
        PircBotX(botConfig).also {
            GlobalScope.async {
                it.startBot()
            }
        }
    }
}