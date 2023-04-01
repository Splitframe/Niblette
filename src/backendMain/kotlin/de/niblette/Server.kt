package de.niblette

import bot.listener.CallbackListener
import bot.listener.botModule
import com.fasterxml.jackson.annotation.JsonProperty
import database.DatabaseConnection
import database.databaseModule
import database.migrateDatabase
import database.request.requestModule
import database.show.ShowRepository
import database.show.aliasModule
import database.show.seasonModule
import database.show.showModule
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin
import routing.configureRouting
import sourceIRCModule
import org.pircbotx.Configuration
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.entry() {

    val prop = Properties().also {
        it.load(FileInputStream("src/backendMain/resources/database.conf"))
    }
    println(prop.getProperty("database.server"))
    val databaseConnection = DatabaseConnection(
        server = prop.getProperty("database.server"),
        port = prop.getProperty("database.port").toInt(),
        driver = prop.getProperty("database.driver"),
        user = prop.getProperty("database.user"),
        password = prop.getProperty("database.password"),
    )

    migrateDatabase(databaseConnection)

    val config: Configuration = Configuration.Builder()
        .setName("Niblette_Kotlin") //Nick of the bot. CHANGE IN YOUR CODE
//        .setLogin("PircBotXUser") //Login part of hostmask, eg name:login@host
        .setAutoNickChange(true)
        .addServer("irc.rizon.net")
        .addAutoJoinChannel("#NIBLM") //Join #pircbotx channel on connect
        .addListener(CallbackListener())
        .buildConfiguration() //Create an immutable configuration from this builder

//    val koinContext = startKoin {
//        modules(
//            showModule(),
//            databaseModule(databaseConnection),
//            botModule(config)
//        )
//    }
    install(Koin) {
        modules(
            showModule(),
            requestModule(),
            sourceIRCModule(),
            seasonModule(),
            aliasModule(),
            databaseModule(databaseConnection),
            botModule(config)
        )
    }
    var testShowRepo = get<ShowRepository>()

    testShowRepo.insertShows("Attack on Titan", "Anime")

//    async { mybot.startBot() }
//    GlobalScope.launch {
//        mybot.startBot()
//    }
    configureRouting()
}