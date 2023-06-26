import csstype.Color
import csstype.number
import csstype.px
import mui.icons.material.Menu
import mui.material.*
import mui.material.styles.TypographyVariant.Companion.h6
import mui.system.sx
import react.FC
import react.Props
import react.dom.aria.ariaLabel
import react.dom.html.ReactHTML.div

external interface AppbarProps : Props {
    var name: String
}
val Appbar = FC<AppbarProps> {
    val currentMenu = "Add Title" //TODO: change Title with react useState depending on current menu.
    Box {
        sx { flexGrow = number(1.0) }

        AppBar {
            sx{
                backgroundColor = Color("#2f3136")
            }
            position = AppBarPosition.static

            Toolbar {
//                IconButton {
//                    sx { marginRight = 2.px }
//                    edge = IconButtonEdge.start
//                    size = Size.large
//                    ariaLabel = "menu"
//
//                    Menu()
//                }

                Typography {
                    sx { flexGrow = number(1.0) }
                    variant = h6
                    component = div

                    +"$currentMenu"
                }

                Button {
                    color = ButtonColor.inherit

                    +"Login"
                }
            }
        }
    }
}
