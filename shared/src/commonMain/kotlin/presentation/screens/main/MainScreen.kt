package presentation.screens.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class MainScreen(
) : Screen {

    @Composable
    override fun Content() {
        mainContent()
    }

    @Composable
    private fun mainContent(
    ) {

        Text("Main Screen")
    }

}
