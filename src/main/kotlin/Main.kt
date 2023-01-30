import database.DatabaseConnection
import database.databaseModule
import database.migrateDatabase
import database.show.ShowRepository
import database.show.showModule
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
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

    startKoin {
        modules(
            showModule(),
            databaseModule(databaseConnection)
        )
    }
}