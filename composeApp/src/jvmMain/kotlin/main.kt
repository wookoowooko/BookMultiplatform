import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import io.ktor.client.engine.okhttp.OkHttp
import io.wookoo.bookapp.core.App
import io.wookoo.bookapp.di.initKoin
import java.awt.Dimension

fun main() {
    initKoin()
    application {
        Window(
            title = "BookMultiplatform",
            state = rememberWindowState(width = 800.dp, height = 600.dp),
            onCloseRequest = ::exitApplication,
        ) {
            window.minimumSize = Dimension(350, 600)
            App(
                engine = remember {
                    OkHttp.create()
                }
            )
        }
    }
}
