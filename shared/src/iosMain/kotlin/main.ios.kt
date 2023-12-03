import androidx.compose.ui.window.ComposeUIViewController
import core.Context


actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App(Context()) }