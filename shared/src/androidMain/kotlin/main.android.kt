import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()


actual class AndroidToastMessage(private val context: Context) : ToastMessage {
    actual override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}


actual interface ToastMessage {
    actual fun showToast(message: String)
}