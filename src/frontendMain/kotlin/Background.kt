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

external interface BackgroundProps : Props {
    var name: String
}

val Background = FC<BackgroundProps> { props ->
    var name by useState(props.name)
    var showArray by useState(mutableListOf<String>())
    div {
        css {
            backgroundColor = Color("#40444b")
            height = 100.vh
            width = 100.pct
            overflow = Overflow.hidden
            margin = 0.px

        }
        div {
            css {
                height = 100.vh
                width = 10.pct
                minWidth = 220.px
                backgroundColor = Color("#2f3136")
                color = Color("#FAF9F6")
            }
            Sidebar{
            }
            Lists{
            }
        }
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
