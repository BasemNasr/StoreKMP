package presentation.screens.main.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import presentation.components.TabNavigationItem
import presentation.screens.auth.login.LoginScreen
import presentation.screens.auth.login.LoginViewModel

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Homeeee",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                style = TextStyle(
                    color = Color.Red
                )
            )

            Button(onClick = {
//                navigator.push(DetailsScreen(id = 1))
            }) {
                Text(text = "Go to example details")
            }
        }
    }
}