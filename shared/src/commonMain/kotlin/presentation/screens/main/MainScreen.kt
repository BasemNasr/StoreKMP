package presentation.screens.main


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import presentation.screens.main.taps.category.CategoryScreen
import presentation.screens.main.taps.category.CategoryTab
import presentation.screens.main.taps.home.HomeScreen
import presentation.screens.main.taps.home.HomeTab
import presentation.screens.main.taps.profile.ProfileTab
import presentation.screens.main.taps.search.SearchTab

object MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        MainScreen.mainContent(navigator)
    }

    @Composable
    private fun mainContent(
        navigator: Navigator? = null,
        viewModel: MainViewModel = koinInject()
    ) {

        val navBackStackEntry = navigator?.lastItem
        val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
        when(navBackStackEntry){
            is HomeScreen ->{
                bottomBarState.value = true
            }
            is CategoryScreen ->{
                bottomBarState.value = false
            }
        }

        TabNavigator(
            tab = HomeTab
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {

                    AnimatedVisibility(
                        visible = bottomBarState.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                    ) {
                        BottomNavigation {
                            TabNavigationItem(HomeTab)
                            TabNavigationItem(CategoryTab)
                            TabNavigationItem(SearchTab)
                            TabNavigationItem(ProfileTab)
                        }
                    }
                },
                content = { CurrentTab() },
            )
        }

    }

}