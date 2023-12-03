
import androidx.compose.foundation.shape.AbsoluteCutCornerShape

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import core.Context
import di.appModule
import org.koin.compose.KoinApplication
import presentation.screens.splash.SplashScreen

@Composable
fun StoreAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@Composable
fun App(context:Context) {
    KoinApplication(application = {
        modules(appModule(context))
    }) {
        StoreAppTheme {
            Navigator(SplashScreen())
        }
    }

}


expect fun getPlatformName(): String


