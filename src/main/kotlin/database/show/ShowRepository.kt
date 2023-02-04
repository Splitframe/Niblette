package database.show

import Shows.Shows
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table.Dual.nullable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ShowRepository(val mariaDb: Database) {
    init {
    }
    fun getShows() = transaction(mariaDb) {
        ShowTable
            .selectAll()
            .map { row ->
                row.mapToShows()
            }

    }
}
object ShowTable: IntIdTable("shows", "id"){
    var name = text("name")
    var description = text("description").nullable()
}

//fun String.addADash(): String{
//    return this + "-"
//}

fun ResultRow.mapToShows() = Shows(
    id = this[ShowTable.id].value,
    name = this[ShowTable.name],
    description = this[ShowTable.description]
)