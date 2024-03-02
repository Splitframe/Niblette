import react.*
import web.window.window
import web.dom.document
import react.dom.client.createRoot
import react.router.*


fun main() {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        document.body.appendChild(root)

//        createRoot(root).render(
//            BrowserRouter.create {
//                children = createElement(Welcome)
//            }
//        )
    }
}

private val App = FC<Props> {
//    BrowserRouter {
//        basename = "/"
//        Background
//    }
}