package presentation.screens.settings


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import data.model.Category
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import presentation.components.CustomDialogSheet
import presentation.components.TabNavigationItem
import presentation.screens.category.CategoryDetailTopBar
import presentation.screens.main.taps.category.CategoryScreen
import presentation.screens.main.taps.category.CategoryTab
import presentation.screens.main.taps.home.HomeScreen
import presentation.screens.main.taps.home.HomeTab
import presentation.screens.main.taps.profile.ProfileTab
import presentation.screens.main.taps.search.SearchTab
import presentation.theme.gray2

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        mainContent(navigator)
    }

    @Composable
    private fun mainContent(
        navigator: Navigator? = null,
    ) {


        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            topBar = { SettingsTopBar(navigator = navigator) },
        ) { paddingValues ->
            Column {

                var showChangeLangSheet by remember { mutableStateOf(false) }

                // region App Language
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "App Language",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF1E1E1E),
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))
//                    val appLanguage = settingsViewModel.selectedLanguage ?: ""
                    TextButton(onClick = {
                        showChangeLangSheet = true
                    }) {
                        Text(
                            text ="English",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                color = gray2,
                            )
                        )
                    }
                }
                if (showChangeLangSheet) {
//                    ChangeAppLanguageSheet(settingsViewModel = settingsViewModel) {
//                        showChangeLangSheet = false
//                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(0xFFE9E8F8))
                )
                // endregion App Language

                Spacer(modifier = Modifier.height(30.dp))

                // region App Ringtone
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "App Ringtone",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF1E1E1E),
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = {

                    }) {
                        Text(
                            text = "Tennessee whiskey",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                color = gray2,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(0xFFE9E8F8))
                )

                // endregion App Ringtone

                Spacer(modifier = Modifier.height(30.dp))

                // region Delete My Account
                var deleteMyAccountSheet by remember { mutableStateOf(false) }

                Row(horizontalArrangement = Arrangement.Start) {
                    TextButton(onClick = {
                        deleteMyAccountSheet = true
                    }) {
                        Text(
                            text = "Delete my account",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFFEF233C),
                            )
                        )
                    }
                }

                if (deleteMyAccountSheet) {
                    CustomDialogSheet(
                        title = "Delete Account",
                        buttonText = "Delete",
                        message = "are you sure you want delete account",
                        onAccept = {
//                            settingsViewModel.onEvent(SettingsEvent.DeleteAccount)
                        },
                        onReject = {
                            deleteMyAccountSheet = false
                        })
                }

            }
        }

    }


    @Composable
    fun SettingsTopBar(navigator: Navigator?=null) {
        TopAppBar(
            title = { Text(text = "Settings") },
            navigationIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        navigator?.pop()
                    },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button"
                )
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
        )
    }


}