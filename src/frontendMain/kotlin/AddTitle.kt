import csstype.Color
import csstype.Display
import csstype.HtmlAttributes.Companion.id
import csstype.JustifyContent
import csstype.PropertyName.Companion.backgroundColor
import csstype.px
import emotion.react.css
import js.core.jso
import mui.material.*
import mui.material.ButtonGroupVariant.Companion.contained
import mui.material.DividerVariant.Companion.fullWidth
import mui.material.FormControlVariant.Companion.outlined
import mui.material.MuiTablePagination.Companion.spacer
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.form

external interface AddTitleProps : Props {
    var name: String
}
val animeGenres = listOf("Action", "Thriller", "Romance", "Shonen")
val AddTitle = FC<AddTitleProps> {
    Box {
        sx {
            display = Display.grid
            justifyContent = JustifyContent.left
            margin = 8.px
        }
        component = form

        Divider {
            variant = fullWidth
        }
//        TextField {
//            id = "title"
//            label = ReactNode("Title")
//            variant = outlined
//        }
        textFieldBuilder("Title")
//        TextField {
//            id = "alias1"
//            label = ReactNode("Alias 1")
//            variant = outlined
//        }
        textFieldBuilder("Alias 1")
//        TextField {
//            id = "alias2"
//            label = ReactNode("Alias 2")
//            variant = outlined
//        }
        textFieldBuilder("Alias 2")

        @Suppress("UPPER_BOUND_VIOLATED")
        Autocomplete<AutocompleteProps<String>> {
            sx {
                width = 300.px
                marginTop = 85.px
                backgroundColor = Color("#97A9B4")
            }
            options = animeGenres.toTypedArray()
            disablePortal = true
            renderInput = { params ->
                TextField.create {
                    +params
                    label = ReactNode("Genre")
                }
            }
        }
        Autocomplete {
            sx {
                width = 300.px
                backgroundColor = Color("#97A9B4")
                marginBottom = 25.px
            }
            disablePortal = true
            renderInput = { params ->
                TextField.create {
                    +params
                    label = ReactNode("Quelle")
                }
            }
        }
        Button {
            sx{
                backgroundColor = Color("#97A9B4")
            }
            variant = ButtonVariant.contained
            +"Add Title"
        }
    }
}

fun ChildrenBuilder.textFieldBuilder(myLabel: String) =
    TextField {
        css{
            backgroundColor = Color("#97A9B4")
            color = Color("#97A9B4")
        }

        id = myLabel.lowercase().filter { !it.isWhitespace() }
        label = ReactNode(myLabel)
        variant = outlined
        margin = FormControlMargin.dense


    }




