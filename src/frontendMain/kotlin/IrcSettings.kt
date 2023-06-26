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
import web.html.HTMLInputElement
import web.html.InputType

//import web.cssom.Length
//import web.cssom.pct
//import web.cssom.px


external interface IrcSettingsProps : Props {
    var name: String
}

val IrcSettings = FC<IrcSettingsProps> {
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
                            +"Server"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Channel"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"Port"
                        }
                        TableCell {
                            align = TableCellAlign.right

                            +"SearchAPI"
                        }
                    }
                }
                TableBody {
                    for (row in IrcSourceTableRows) {
                        TableRow {
                            key = row.server

//                            TableCell {
//                                component = th
//                                scope = "row"
//
//                                +row.name
//                            }
                            tableCellWhiteIrcSettings("${row.server}", myAlign = TableCellAlign.left)
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.alt}"
//                            }
                            tableCellWhiteIrcSettings("${row.channel}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Genre}"
//                            }
                            tableCellWhiteIrcSettings("${row.port}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Seasons}"
//                            }
                            tableCellWhiteIrcSettings("${row.searchAPI}")
//                            TableCell {
//                                align = TableCellAlign.right
//
//                                +"${row.Episodes}"
//                            }
                        }
                    }
                }
            }
        }
    }
    Box {
        sx{
            justifyContent = JustifyContent.center
            width = 80.pct
            margin = Auto.auto
            marginTop = 4.pct
            display = Display.grid
        }
        Divider {
            variant = fullWidth

            Chip {
                label = ReactNode("Neues IRC")
            }
        }

        inputWhite("Server")
        inputWhite("Channel")
        inputWhiteInt(3313)
        inputWhite("searchAPI")
    }
}


private data class IrcSourceTableData(
    val server: String,
    val channel: String,
    val port: Int,
    val searchAPI: String
)

private val IrcSourceTableRows = setOf(
    IrcSourceTableData("", "", 1,"")
)

fun ChildrenBuilder.tableCellWhiteIrcSettings(myTableValue: String, myAlign: TableCellAlign = TableCellAlign.right) =
    TableCell {
        sx {
            color = white
        }
        align = myAlign
        +myTableValue
    }

fun ChildrenBuilder.inputWhite(placeholderIn: String) =
    Input {
        sx {
            color = white
        }
        placeholder = placeholderIn
    }

fun ChildrenBuilder.inputWhiteInt(placeholderIn: Int) {
    var hasError by useState(false)
    Input {
        sx {
            color = white
        }
        error = hasError
        onChange = { event ->
            val target = event.target as HTMLInputElement
            hasError = !isOnlyNumbers(target.value)
        }
        placeholder = placeholderIn.toString()
    }
}

fun isOnlyNumbers(port: String): Boolean {
    val regex = "^[0-9]+$".toRegex()
    return regex.matches(port)
}

