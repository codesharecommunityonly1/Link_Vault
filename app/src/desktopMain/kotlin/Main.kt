import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.linkvault.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Link Vault",
    ) {
        App()
    }
}
