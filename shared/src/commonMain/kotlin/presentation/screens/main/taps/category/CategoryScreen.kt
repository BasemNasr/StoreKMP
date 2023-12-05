package presentation.screens.main.taps.category


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi

object CategoryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        mainContent(navigator)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun mainContent(
        navigator: Navigator? = null,
    ) {

       Text("Cart Screen")
        /*val userToken = viewModel?.userToken?.value
        viewModel.setStateEvent(MainStateIntent.GettingToken)
        Text("Hello Your Token is : $userToken")*/

    }


}