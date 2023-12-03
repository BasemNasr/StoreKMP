import android.app.Application
import androidx.compose.runtime.Composable
import core.Context

actual fun getPlatformName(): String = "Android"

@Composable
fun MainView(application: Application) = App(application)

