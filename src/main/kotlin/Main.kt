import database.DatabaseConnection
import database.databaseModule
import database.migrateDatabase
import database.show.ShowRepository
import database.show.showModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.koin.core.context.startKoin
import org.pircbotx.Configuration
import org.pircbotx.PircBotX
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {
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

    val koinContext = startKoin {
        modules(
            showModule(),
            databaseModule(databaseConnection)
        )
    }
    println(koinContext.koin.get<ShowRepository>().getShows().first().name)
    println(PircBotX.VERSION)

    val config: Configuration = Configuration.Builder()
        .setName("Niblette_Kotlin") //Nick of the bot. CHANGE IN YOUR CODE
        .setLogin("PircBotXUser") //Login part of hostmask, eg name:login@host
        .setAutoNickChange(true)
        .addServer("irc.rizon.net")
        .addAutoJoinChannel("#NIBL") //Join #pircbotx channel on connect
        .buildConfiguration() //Create an immutable configuration from this builder

    val myBot = PircBotX(config)


    GlobalScope.async {
        myBot.startBot()
    }
    println("test")
    while (!myBot.isConnected) {
        Thread.sleep(100)
    }
    myBot.sendIRC().message("#NIBL", "!s Bleach")
    while (true){
        Thread.sleep(1000)
    }
}


/*
-Im IRC Chat einloggen.
-Nibl.co msg fuer entsprechenden Anime raussuchen.
-Episodeninformationen rausparsen. (Name, Season, Episode, Aufloesung)
-Im IRC Chat bot mit msg anwhispern.
-Download entgegen nehmen.
-Datei irgendwo abspeichern. (Datei richtig im Jellyfin System hinterlegen.)
-Public Chat ueberwachen & Neue Folgen runterladen.
-




 */