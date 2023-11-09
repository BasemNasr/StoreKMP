package presentation.screens.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import presentation.components.TabNavigationItem
import presentation.screens.main.taps.category.CategoryTab
import presentation.screens.main.taps.home.HomeTab
import presentation.screens.main.taps.profile.ProfileTab
import presentation.screens.main.taps.search.SearchTab

class MainScreen(
) : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

        TabNavigator(
            HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab, CategoryTab, SearchTab, ProfileTab)
                )
            }
        ) {
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(CategoryTab)
                        TabNavigationItem(SearchTab)
                        TabNavigationItem(ProfileTab)

                    }
                },
            ) {
                CurrentTab()
            }
        }


    }


}

