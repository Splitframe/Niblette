import database.DatabaseConnection
import database.databaseModule
import database.migrateDatabase
import database.show.ShowRepository
import database.show.showModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import bot.listener.CallbackListener
import bot.listener.botModule
import database.request.requestModule
import database.show.aliasModule
import database.show.seasonModule
import io.ktor.server.application.*
import io.ktor.server.application.hooks.CallFailed.install
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.launch
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin
import org.pircbotx.Configuration
import org.pircbotx.PircBotX
import routing.configureRouting
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {
//    while(true) {
//        println("Running...")
//        Thread.sleep(1000)
//    }
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::start)
        .start(wait = true)
}

fun Application.start() {
    val prop = Properties().also {
        it.load(FileInputStream("src/main/resources/application.conf"))
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



/*
-Im IRC Chat einloggen. X
-Nibl.co msg fuer entsprechenden Anime raussuchen.
-Episodeninformationen rausparsen. (Name, Season, Episode, Aufloesung)
-Im IRC Chat bot mit msg anwhispern. X
-Download entgegen nehmen. X
-Datei irgendwo abspeichern. X (Datei richtig im Jellyfin System hinterlegen.)
-Public Chat ueberwachen & Neue Folgen runterladen.
 */