import csstype.*
import emotion.react.css
import js.core.jso
import react.*
import react.dom.html.ReactHTML.div
import react.router.*
import remix.run.router.matchPath
import web.cssom.*
import web.location.location

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

        }
        Routes {
            Route {
                this.asDynamic().path = "/"
                this.asDynamic().element = createElement(Sidebar)
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
