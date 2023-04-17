import web.window.window
import web.dom.document
import react.create
import react.dom.client.createRoot


fun main() {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        document.body.appendChild(root)

        val welcome = Background.create {
            name = "Kotlin/JS"
        }
        createRoot(root).render(welcome)
    }
}