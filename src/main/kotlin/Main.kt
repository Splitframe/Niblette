import database.show.ShowRepository
import database.show.showModule
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

fun main(args: Array<String>) {
    startKoin {
        modules(showModule())
    }
}