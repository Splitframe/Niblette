package database.show

import Requests.SourceIRC
import Shows.Season
import database.request.RequestsTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SeasonRepository(val mariaDb: Database) {
    init {
    }
    fun getRepository() = transaction(mariaDb) {
        SeasonTable
            .selectAll()
            .map {row ->
                row.mapToSeason()
            }

    }
}

object SeasonTable: IntIdTable("season", "id"){

    var seasonId = integer("seasonid")
    var showId = integer("showId")
    var rootSeason = integer("rootSeason")
    var subSeason = integer("subSeason")
    var name = text("name")
    var episodes = integer("episodes")
}

fun ResultRow.mapToSeason() = Season(
    seasonId = this[SeasonTable.seasonId],
    showId = this[SeasonTable.showId],
    rootSeason = this[SeasonTable.rootSeason],
    subSeason = this[SeasonTable.subSeason],
    name = this[SeasonTable.name],
    episodes = this[SeasonTable.episodes],
)

