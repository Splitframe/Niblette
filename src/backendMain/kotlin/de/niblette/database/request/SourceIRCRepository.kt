import Requests.Requests
import Requests.SourceIRC
import database.request.RequestsTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SourceIRCRepository(val mariaDb: Database) {
    init {
    }
    fun getRepository() = transaction(mariaDb) {
        SourceIRCTable
            .selectAll()
            .map {row ->
                row.mapToSourceIRC()
            }

    }
}

object SourceIRCTable: IntIdTable("sourceIRC", "id"){
    var server = text("server")
    var channel = text("channel")
    var port = integer("port")
    var searchAPI = text("searchAPI")
}

fun ResultRow.mapToSourceIRC() = SourceIRC(

    id = this[RequestsTable.sourceIRCId],
    server = this[SourceIRCTable.server],
    channel = this[SourceIRCTable.channel],
    port = this[SourceIRCTable.port],
    searchAPI = this[SourceIRCTable.searchAPI]
    )