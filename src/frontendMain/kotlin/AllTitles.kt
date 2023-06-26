import csstype.*
import csstype.NamedColor.Companion.white
import mui.material.TableContainer
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


external interface AllTitlesProps : Props {
    var name: String
}

val AllTitles = FC<AllTitlesProps> {
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

                            +"alt. Title"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Genre"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Seasons"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Episodes"
                        }
                    }
                }
                TableBody {
                    for (row in basicTableRows) {
                        TableRow {
                            key = row.name

//                            TableCell {
//                                component = th
//                                scope = "row"
//
//                                +row.name
//                            }
                            tableCellWhite("${row.name}", myAlign = TableCellAlign.left)
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.alt}"
//                            }
                            tableCellWhite("${row.alt}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Genre}"
//                            }
                            tableCellWhite("${row.Genre}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Seasons}"
//                            }
                            tableCellWhite("${row.Seasons}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Episodes}"
//                            }
                            tableCellWhite("${row.Episodes}")
                        }
                    }
                }
            }
        }
    }
}

private data class BasicTableData(
    val name: String,
    val alt: String,
    val Genre: String,
    val Seasons: Int,
    val Episodes: Int,
)

private val basicTableRows = setOf(
    BasicTableData("Bleach","", "Shonen", 5, 13),
    BasicTableData("Naruto","", "Shonen", 5, 13),
    BasicTableData("Shitpiece","", "Shonen", 5, 13),
    BasicTableData("Neon God evangelion","", "Shonen", 5, 13),
    BasicTableData("Bleach","", "Shonen", 5, 13)
)

fun ChildrenBuilder.tableCellWhite(myTableValue: String, myAlign: TableCellAlign = TableCellAlign.right) =
    TableCell {
        sx {
            color = white
        }
        align = myAlign
        +myTableValue
    }


