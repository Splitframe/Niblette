package database.request

import Requests.Requests
import Shows.Shows
import database.show.ShowTable
import database.show.ShowTable.nullable
import database.show.mapToShows
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class RequestRepository(val mariaDb: Database) {
    init {
    }
    fun getRepository() = transaction(mariaDb) {
        RequestsTable
            .selectAll()
            .map {row ->
                row.mapToRequests()
            }

    }
}

object RequestsTable: IntIdTable("requests", "id"){
    var showId = integer("showId")
    var sourceIRCId = integer("sourceIRCId")
}

fun ResultRow.mapToRequests() = Requests(

    id = this[RequestsTable.id].value,
    showId = this[RequestsTable.showId],
    sourceIRCId = this[RequestsTable.sourceIRCId],

)