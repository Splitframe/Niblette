import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.get
import react.create
import react.dom.client.createRoot

fun main() {
    window.onload = {
        val root = document.getElementById("root") ?: throw IllegalStateException()
        document.body!!.appendChild(root)

        val welcome = Welcome.create {
            name = "Kotlin/JS"
        }
        createRoot(root).render(welcome)
    }
}