package database.show

import org.koin.dsl.module

fun seasonModule() = module {
    single<SeasonRepository>() {
        SeasonRepository(mariaDb = get())
    }
}