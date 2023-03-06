package database.show

import org.koin.dsl.module

fun aliasModule() = module {
    single<AliasRepository>() {
        AliasRepository(mariaDb = get())
    }
}