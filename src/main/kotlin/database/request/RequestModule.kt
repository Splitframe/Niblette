package database.request

import database.show.ShowRepository
import org.koin.dsl.module

fun requestModule() = module {
    single<RequestRepository>() {
        RequestRepository(mariaDb = get())
    }
}