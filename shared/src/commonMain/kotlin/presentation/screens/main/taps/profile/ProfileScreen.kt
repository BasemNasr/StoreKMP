package presentation.screens.main.taps.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.network.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.components.AppSlider
import presentation.components.CategoryCardTag
import presentation.components.CustomDialogSheet
import presentation.components.LoadingAnimation3
import presentation.components.ProductCard
import presentation.components.ProfileSectionCard
import presentation.screens.auth.updateProfile.UpdateProfileScreen
import presentation.screens.product.ProductDetailsScreen
import presentation.screens.settings.SettingsScreen
import presentation.theme.gray2
import utils.AppStrings

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator :Navigator = LocalNavigator.currentOrThrow
        val viewModel: ProfileViewModel = koinInject()

        var showLogOutSheet by remember { mutableStateOf(false) }
        val logoutState = viewModel.logout.collectAsState()


        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Profile",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Black,

                    ),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Card(modifier = Modifier.size(80.dp), shape = CircleShape) {
                    KamelImage(
                        asyncPainterResource("https://i.ibb.co/cyP8x9m/my-passport-photo.jpg"),
                        contentDescription = "profile_pic"
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Mohamed Sakr",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            color = Color.Black,
                        )
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "sagr3272@gmail.com",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(600),
                            color = gray2,
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Spacer(modifier = Modifier.weight(1f))


                }



                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier
                        .clickable {
                            navigator?.push(UpdateProfileScreen)
                        }
                        .size(24.dp),
                    tint = Color.Black,
                    imageVector = Icons.Default.Edit,
                    contentDescription = ""
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            Column(
                Modifier.verticalScroll(rememberScrollState())

            ) {
                ProfileSectionCard({ }, title = "My Orders") {
                }

                ProfileSectionCard({ }, title = "Cart") {

                }

                ProfileSectionCard(
                    { },
                    title = "Settings"
                ) {
                    navigator?.push(SettingsScreen)
                }
                ProfileSectionCard(
                    {
                    },
                    color = Color.Red,
                    withLine = false,
                    title = "Logout"
                ) {
                    showLogOutSheet = true
                }

            }


            if (showLogOutSheet) {
                CustomDialogSheet(
                    title = AppStrings.log_out.stringValue,
                    buttonText = AppStrings.log_out.stringValue,
                    message = AppStrings.are_you_sure_you_want_log_out.stringValue,
                    onAccept = {
                        showLogOutSheet = false
                        viewModel.setStateEvent(ProfileStateIntent.LogoutUser)
                    },
                    onReject = {
                        showLogOutSheet = false
                    })
            }

        }

        when (logoutState?.value) {
            true -> {
                navigator?.popUntilRoot()
            }
            null -> {}
            else -> {}
        }
    }
}