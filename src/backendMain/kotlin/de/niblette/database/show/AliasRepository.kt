package database.show

import Shows.Alias
import Shows.Season
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AliasRepository(val mariaDb: Database) {
    init {
    }
    fun getRepository() = transaction(mariaDb) {
        AliasTable
            .selectAll()
            .map {row ->
                row.mapToAlias()
            }

    }
}

object AliasTable: IntIdTable("alias", "id"){

    var alias = text("alias")
    var showId = integer("showId")
}

fun ResultRow.mapToAlias() = Alias(
    alias = this[AliasTable.alias],
    showId = this[AliasTable.showId]

)
