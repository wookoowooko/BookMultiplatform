import androidx.compose.ui.window.ComposeUIViewController
import io.wookoo.bookapp.core.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
