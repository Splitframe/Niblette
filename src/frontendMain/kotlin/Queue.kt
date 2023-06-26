import csstype.*
import csstype.NamedColor.Companion.white
import csstype.PropertyName.Companion.padding
import mui.material.Divider
import mui.material.TableContainer
import js.core.jso
import mui.material.*
import mui.material.DividerVariant.Companion.fullWidth
import mui.system.sx
import react.*
import react.dom.aria.AriaRole
import react.dom.aria.ariaLabel
import react.dom.html.ReactHTML.th
//import web.cssom.Length
//import web.cssom.pct
//import web.cssom.px


external interface QueueProps : Props {
    var name: String
}

val Queue = FC<QueueProps> {
    Box {
        sx{
            justifyContent = JustifyContent.center
            width = 80.pct
            margin = Auto.auto
            marginTop = 8.pct
        }
        TableContainer {
//        component = Paper.create().type

            Table {
                sx {
                    minWidth = 650.px
                    justifyContent = JustifyContent.left
                }
                ariaLabel = "simple table"

                TableHead {
                    TableRow {
                        TableCell {
                            +"Title"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Queue #"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Genre"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Season"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Episode"
                        }
                    }
                }
                TableBody {
                    for (row in queueTableRows) {
                        TableRow {
                            key = row.name

//                            TableCell {
//                                component = th
//                                scope = "row"
//
//                                +row.name
//                            }
                            tableCellWhiteQueue("${row.name}", myAlign = TableCellAlign.left)
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.alt}"
//                            }
                            tableCellWhiteQueue("${row.queueplace}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Genre}"
//                            }
                            tableCellWhiteQueue("${row.Genre}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Seasons}"
//                            }
                            tableCellWhiteQueue("${row.Season}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Episodes}"
//                            }
                            tableCellWhiteQueue("${row.Episode}")
                        }
                    }
                }
            }
        }
    }
}

private data class QueueTableData(
    val name: String,
    val queueplace: Int?,
    val Genre: String,
    val Season: Int,
    val Episode: Int,
)

private val queueTableRows = setOf(
    QueueTableData("Bleach", 1, "Shonen", 3, 14),
    QueueTableData("Naruto", 2, "Shonen", 8, 24),
    QueueTableData("Shitpiece", 3, "Adventure", 56, 1100),
    QueueTableData("Neon God evangelion", 4, "Psycho", 4, 12),
    QueueTableData("Bleach", 5, "Shonen", 8, 12)
)

fun ChildrenBuilder.tableCellWhiteQueue(myTableValue: String, myAlign: TableCellAlign = TableCellAlign.right) =
    TableCell {
        sx {
            color = white
        }
        align = myAlign
        +myTableValue
    }


