import csstype.*
import csstype.Cursor.Companion.text
import csstype.PropertyName.Companion.margin
import emotion.react.css
import js.core.jso
import mui.material.*
import mui.system.ThemeProvider
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.router.*
import react.router.RelativeRoutingType.Companion.path
import react.router.RelativeRoutingType.Companion.route
import react.router.dom.createBrowserRouter
import web.html.InputType

external interface BackgroundProps : Props {
    var name: String
}

val Background = FC<BackgroundProps> { props ->


    div {
        css {
            backgroundColor = Color("#40444b")
            height = 100.vh
            width = 100.pct
            overflow = Overflow.hidden
            margin = 0.px
            display = Display.flex
        }
        div {
            css {
                height = 100.vh
                width = 10.pct
                minWidth = 220.px
                backgroundColor = Color("#2f3136")
                color = Color("#FAF9F6")
            }
            Sidebar {
            }
        }
        div {
            css {
                height = 4.pct
                width = 90.pct
                backgroundColor = Color("#FAF9F6")
                color = Color("#2f3136")
                display = Display.flex
                flexDirection = FlexDirection.column
            }
            Appbar {
            }
            IrcSettings {
            }
//            Queue {
//            }
//            AllTitles{
//            }
//            AddTitle {
//            }
        }
//        div {
//            css{
//                width = 90.pct
//            }
//            AddTitle{
//            }
//        }
    }

//    Erst image.kt, dann lists.kt aus github/mui/karakum-team/showcase bla
    /**
    //
    //    input {
    //        css {
    //            marginTop = 5.px
    //            marginBottom = 5.px
    //            fontSize = 14.px
    //        }
    //        type = InputType.text
    //        value = name
    //        onChange = { event ->
    //            name = event.target.value
    //        }
    //    }
    //
    //    @Suppress("UPPER_BOUND_VIOLATED")
    //    Autocomplete<AutocompleteProps<String>> {
    //        sx { width = 300.px }
    //        options = showArray.toTypedArray()
    //        disablePortal = false
    //        renderInput = { params ->
    //            TextField.create {
    //                +params
    //                label = ReactNode("Movie")
    //            }
    //        }
    //        onChange = { _, _, _, changeDetails ->
    //            name = changeDetails?.option.toString()
    //        }
    //    }
    Button {
    variant = ButtonVariant.text
    +"click me"
    onClick = {
    showArray.add("click - the movie")
    println(showArray)
    }
    }
     */
}
