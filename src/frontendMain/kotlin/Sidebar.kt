import emotion.css.css
import react.FC
import react.Props
import react.useState
import mui.material.*
import mui.system.sx
import mui.material.ImageListItem
import react.dom.html.ReactHTML.img
import mui.system.PropsWithSx
import react.PropsWithChildren
import web.cssom.*

external interface ListsProps : Props {
    var name: String
}

val Sidebar = FC<ListsProps> { props ->
    var name by useState(props.name)
    var showArray by useState(mutableListOf<String>())



    Box {
        sx {
            height = 100.vh
            width = 10.pct
            minWidth = 220.px
            backgroundColor = Color("#2f3136")
            color = Color("#FAF9F6")
        }

        List {
            sx {
                width = 100.pct
                maxWidth = 360.px
            }

            ImageListItem {
                img {
                    src = "https://i.pinimg.com/originals/70/ec/4a/70ec4ad13386197ce303a886e9686274.jpg"
                }
            }

            ListItem {
                sx {
                    borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
                }
                disablePadding = true
                divider = true
                ListItemButton {
                    ListItemIcon {
//                        Reorder {
//                            sx {
//                                color = Color("#FAF9F6")
//                            }
//                        }
                    }
                    ListItemText {
                        +"Alle Titel"
                    }
                }
            }
            ListItem {
                sx {
                    borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
                }
                disablePadding = true
                divider = true
                ListItemButton {
                    ListItemIcon {
//                        LibraryAdd{
//                            htmlColor = "#FAF9F6"
//                        }
                    }
                    ListItemText {
                        sx {
                            whiteSpace = WhiteSpace.nowrap
                        }
                        +"Titel hinzufügen"
                    }
                }
            }
            ListItem {
                sx {
                    borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
                }
                disablePadding = true
                divider = true
                ListItemButton {
                    ListItemIcon {
//                        Subscriptions{
//                            sx {
//                                color = Color("#FAF9F6")
//                            }
//                        }
                    }
                    ListItemText {
                        +"Queue"
                    }
                }
            }
            ListItem {
                sx {
                    borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
                }
                disablePadding = true
                divider = false
                ListItemButton {
                    ListItemIcon {
//                        Settings{
//                            sx {
//                                color = Color("#FAF9F6")
//                            }
//                        }
                    }
                    ListItemText {
                        +"Einstellungen"
                    }
                }
            }
        }
    }
}

// einzelne ListItems in Funktion auslagern, welche den Iconnamen und den Labelnamen entgegen nehmen und gleiches Ergebnis liefern.
// tldr: machs cleaner du goon