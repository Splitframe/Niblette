package database.show

import org.koin.dsl.module

fun showModule() = module {
    single<ShowRepository>() {
        ShowRepository(mariaDb = get())
    }

}