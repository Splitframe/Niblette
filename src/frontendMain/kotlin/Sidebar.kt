import csstype.*
import csstype.LineStyle
import mui.icons.material.*
import mui.material.*
import mui.system.sx
import mui.material.ImageListItem
import mui.material.List
import react.*
import react.dom.html.ReactHTML.img

external interface ListsProps : Props {
    var name: String
}
class myFakeDb {
    fun insertShow(name: String, release: String, episodes: Int, watching: Boolean, finished: Boolean, rating: Int ) {
        println(name)
        println(release)
        println(episodes)
        println(watching)
        println(finished)
        println(rating)
    }
    fun insertShow(name: String, release: String, episodes: Int, watching: Boolean, finished: Boolean) = insertShow(name, release, episodes, watching, finished, 0)
    fun insertShow(name: String, release: String, episodes: Int, watching: Boolean) = insertShow(name, release, episodes, watching, false, 0)
    fun insertShow(name: String, release: String, episodes: Int) = insertShow(name, release, episodes, false, false, 0)
}
val myvar1 = ""
fun myFunction3() = myFunction1()
fun myFunction2() = myvar1
fun myFunction1() {

}
fun ChildrenBuilder.sidebarItemBuilder(iconName: SvgIconComponent, labelName: String) =
    ListItem {
        sx {
            borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
        }
        disablePadding = true
        divider = true
        ListItemButton {
            ListItemIcon {
                iconName {
                    sx {
                        color = Color("#FAF9F6")
                    }
                }
            }
            ListItemText {
                +labelName
            }
        }
    }


val Sidebar = FC<ListsProps> { props ->
    var name by useState(props.name)
    var showArray by useState(mutableListOf<String>())


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
//<editor-fold desc = "ListItem">
//        ListItem {
//            sx {
//                borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
//            }
//            disablePadding = true
//            divider = true
//            ListItemButton {
//                ListItemIcon {
//                    Reorder {
//                        sx {
//                            color = Color("#FAF9F6")
//                        }
//                    }
//                }
//                ListItemText {
//                    +"Alle Titel"
//                }
//            }
//        }
// </editor-fold>

        sidebarItemBuilder(Reorder, "Alle Titel")
//        ListItem {
//            sx {
//                borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
//            }
//            disablePadding = true
//            divider = true
//            ListItemButton {
//                ListItemIcon {
//                    LibraryAdd{
//                        htmlColor = "#FAF9F6"
//                    }
//                }
//                ListItemText {
//                    sx {
////                        minWidth = 250.px
//                        whiteSpace = WhiteSpace.nowrap
//                    }
//                    +"Titel hinzufügen"
//                }
//            }
//        }
        sidebarItemBuilder(LibraryAdd, "Titel hinzufügen")
//        ListItem {
//            sx {
//                borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
//            }
//            disablePadding = true
//            divider = true
//            ListItemButton {
//                ListItemIcon {
//                    Subscriptions{
//                        sx {
//                            color = Color("#FAF9F6")
//                        }
//                    }
//                }
//                ListItemText {
//                    +"Queue"
//                }
//            }
//        }
        sidebarItemBuilder(Subscriptions, "Queue")
//        ListItem {
//            sx {
//                borderBottom = Border(1.px, LineStyle.solid, Color("#FAF9F6"))
//            }
//            disablePadding = true
//            divider = true
//            ListItemButton {
//                ListItemIcon {
//                    Settings{
//                        sx {
//                            color = Color("#FAF9F6")
//                        }
//                    }
//                }
//                ListItemText {
//                    +"Einstellungen"
//                }
//            }
//        }
        sidebarItemBuilder(Settings, "Einstellungen")

    }
//    TODO:
//    Button{
//        onClick = {
//
//            useEffect{
//                window.scrollTo(0.0,0.0)
//            }
//        }
//        variant = ButtonVariant.text
//        +"scroll button"
//    }
}


// einzelne ListItems in Funktion auslagern, welche den Iconnamen und den Labelnamen entgegen nehmen und gleiches Ergebnis liefern.
// tldr: machs cleaner du goon