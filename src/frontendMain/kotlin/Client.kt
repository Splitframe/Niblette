import csstype.PrintColorAdjust.Companion.exact
import react.FC
import react.Props
import web.window.window
import web.dom.document
import react.create
import react.dom.client.createRoot
import react.router.RelativeRoutingType.Companion.path
import react.router.Route
import react.router.RouterProvider
import react.router.Routes
import react.router.dom.BrowserRouter
import remix.run.router.ImmutableRouteKey.Companion.path


fun main() {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        document.body.appendChild(root)

        val welcome = Background.create {
        }
        createRoot(root).render(welcome)
    }
}
//private val App = FC<Props> {
//    RouterProvider {
//        BrowserRouter {
//            basename = "/"
//            Background
//        }
//    }
//}
