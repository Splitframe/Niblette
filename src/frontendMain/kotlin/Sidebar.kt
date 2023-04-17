import csstype.*
import csstype.Cursor.Companion.text
import csstype.PropertyName.Companion.margin
import emotion.react.css
import mui.material.*
import mui.system.ThemeProvider
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import web.html.InputType
import csstype.pct
import csstype.px
import mui.icons.material.Star
import mui.material.*
import mui.system.sx
import react.FC
import react.Props


external interface SidebarProps : Props {
    var name: String
}

val Sidebar = FC<SidebarProps> { props ->
    var name by useState(props.name)
    var showArray by useState(mutableListOf<String>())

//    Button {
//        variant = ButtonVariant.text
//        +"click me"
//        onClick = {
//            showArray.add("click - the movie")
//            println(showArray)
//        }
//    }
}
