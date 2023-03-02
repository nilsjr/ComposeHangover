import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.nilsdruyen.compose.common.HangoverViewModel
import de.nilsdruyen.compose.common.data.HangoverRepositoryImpl
import de.nilsdruyen.compose.desktop.App
import kotlinx.coroutines.MainScope

fun main() = application {
    val scope = MainScope()
    val repository = HangoverViewModel(scope, HangoverRepositoryImpl())
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Hangover"
    ) {
        App(repository)
    }
}