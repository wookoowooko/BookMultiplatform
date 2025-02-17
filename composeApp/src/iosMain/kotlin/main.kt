import androidx.compose.ui.window.ComposeUIViewController
import io.wookoo.bookapp.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
