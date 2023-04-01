import database.request.RequestRepository
import org.koin.dsl.module

fun sourceIRCModule() = module {
    single<SourceIRCRepository>() {
        SourceIRCRepository(mariaDb = get())
    }
}