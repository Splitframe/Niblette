import emotion.react.css
import mui.material.*
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.router.*
import remix.run.router.Path
import remix.run.router.matchPath
import web.cssom.*
import web.html.HTMLInputElement
import web.html.InputType

external interface WelcomeProps : Props {
    var name: String
}
//private external interface Movie {
//    var label: String
//    var year: Int
//}
//
//private val top100Films = arrayOf(
//    Movie("The Shawshank Redemption", 1994),
//    Movie("The Godfather", 1972),
//    Movie("The Godfather: Part II", 1974),
//    Movie("The Dark Knight", 2008),
//    Movie("12 Angry Men", 1957),
//)
//private fun Movie(label: String, year: Int): Movie = jso {
//    this.label = label
//    this.year = year
//}

val Welcome = FC<WelcomeProps> { props ->
    var name by useState(props.name)
    var showArray by useState(mutableListOf<String>())

    div {
        css {
            padding = 5.px
            backgroundColor = rgb(8, 97, 22)
            color = rgb(56, 246, 137)
            color = Color("White")
            margin = Auto.auto
        }
        +"Hello, $name"
    }

    input {
        css {
            marginTop = 5.px
            marginBottom = 5.px
            fontSize = 14.px
        }
        type = InputType.text
        value = name
        onChange = { event ->
            name = event.target.value
        }
    }
    Input {
        onChange = {
            val target = it.target as HTMLInputElement
            println(target.value)
        }
    }

    @Suppress("UPPER_BOUND_VIOLATED")
    Autocomplete<AutocompleteProps<String>> {
        sx { width = 300.px }
        options = showArray.toTypedArray()
        disablePortal = false
        renderInput = { params ->
            TextField.create {
                +params
                label = ReactNode("Movie")
            }
        }
        onChange = { _, _, _, changeDetails ->
            name = changeDetails?.option.toString()
        }
    }
    Button {
        variant = ButtonVariant.text
        +"click me"
        onClick = {
            showArray.add("click - the movie")
            println(showArray)
        }
    }
}

fun ChildrenBuilder.myTextfield() = TextField {
    
}

