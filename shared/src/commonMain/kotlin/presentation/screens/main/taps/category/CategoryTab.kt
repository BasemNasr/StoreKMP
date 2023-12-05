@file:OptIn(ExperimentalAnimationApi::class)

package presentation.screens.main.taps.category

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import presentation.screens.main.taps.category.CategoryScreen

object CategoryTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Cart"
            val icon = rememberVectorPainter(Icons.Default.Menu)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = CategoryScreen) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
