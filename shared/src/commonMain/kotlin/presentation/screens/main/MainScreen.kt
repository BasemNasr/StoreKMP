package presentation.screens.main


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import presentation.components.TabNavigationItem
import presentation.screens.main.taps.category.CategoryTab
import presentation.screens.main.taps.home.HomeTab

object MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        MainScreen.mainContent(navigator)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun mainContent(
        navigator: Navigator? = null,
        viewModel: MainViewModel = koinInject()
    ) {

        TabNavigator(
            tab = HomeTab
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(CategoryTab)
                    }
                },
                content = { CurrentTab() },
            )
        }
        /*val userToken = viewModel?.userToken?.value
        viewModel.setStateEvent(MainStateIntent.GettingToken)
        Text("Hello Your Token is : $userToken")*/

    }


}