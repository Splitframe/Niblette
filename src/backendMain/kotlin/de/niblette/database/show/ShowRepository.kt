package database.show

import Shows.Shows
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
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

    fun insertShows(showName: String, showCategory: String) = transaction(mariaDb) {
        ShowTable
            .insertAndGetId {
                it[name] = showName
                it[category] = showCategory
            }
    }
}
object ShowTable: IntIdTable("shows", "id"){
    var name = text("name")
    var category = text("category").nullable()
}

//fun String.addADash(): String{
//    return this + "-"
//}

fun ResultRow.mapToShows() = Shows(
    id = this[ShowTable.id].value,
    name = this[ShowTable.name],
    category = this[ShowTable.category]
)

