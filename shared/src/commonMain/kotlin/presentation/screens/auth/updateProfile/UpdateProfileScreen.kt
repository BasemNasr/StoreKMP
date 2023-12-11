package presentation.screens.auth.updateProfile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.model.Product
import data.model.TextFieldState
import data.network.Resource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.components.AppPrimaryButton
import presentation.components.AppTextField
import presentation.screens.auth.login.LoginScreen
import presentation.screens.auth.login.LoginUIStateEvent
import presentation.screens.main.MainScreen
import presentation.screens.main.taps.home.HomeScreen
import presentation.screens.product.DetailTopBar
import presentation.theme.BOLD_SILVER_BACKGROUND_COLOR
import presentation.theme.DarkPurple
import presentation.theme.Typography
import presentation.theme.gray2
import presentation.theme.textColorSemiBlack
import presentation.theme.yellow
import utils.AppStrings

object UpdateProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        updateProfileContent(navigator)
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun updateProfileContent(
        navigator: Navigator? = null,
        viewModel: UpdateProfileViewModel = koinInject()
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(BOLD_SILVER_BACKGROUND_COLOR)
                .padding(horizontal = 16.dp),
            topBar = { UpdateProfileTopBar(navigator = navigator) },
        ) { paddingValues ->

            val nameState = viewModel.userName.value
            val emailState = viewModel.email.value
            val passwordState = viewModel.password.value

            val registerState = viewModel.updateProfile?.collectAsState()


            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(BOLD_SILVER_BACKGROUND_COLOR)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource("compose-multiplatform.xml"),
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp)
                            .padding(top = 60.dp)
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        contentDescription = null
                    )

                    BoxRegisterDataInputs(nameState, passwordState, emailState, viewModel)

                    //Spacer(modifier = Modifier.height(40.dp))

                    AppPrimaryButton(
                        modifier = Modifier
                            .padding(horizontal = 40.dp, vertical = 12.dp)
                            .width(400.dp)
                            .height(90.dp),
                        buttonText = AppStrings.update_profile.stringValue,
                        isLoading = registerState?.value is Resource.Loading,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = yellow,
                            backgroundColor = DarkPurple,
                        ),
                        textColor = yellow,
                        style = TextStyle(
//                        fontFamily = fontFamilyResource(Font),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    ) {
                        viewModel.setStateEvent(UpdateProfileStateIntent.UpdateProfile)
                    }



                    Spacer(modifier = Modifier.height(20.dp))


                }
            }
            val snackState = remember { SnackbarHostState() }
            val snackScope = rememberCoroutineScope()

            SnackbarHost(hostState = snackState, Modifier)

            fun launchSnackBar(message: String) {
                snackScope.launch { snackState.showSnackbar("$message") }
            }



            when (registerState?.value) {
                is Resource.Success -> {
                    navigator?.pop()
                }

                is Resource.Failure -> {
                    launchSnackBar("${(registerState?.value as Resource.Failure)?.exception?.message.toString()}")
                    viewModel.clearAllState()
                }

                Resource.Loading -> {

                }

                null -> {}
            }
        }


    }

    @Composable
    private fun BoxRegisterDataInputs(
        userName: TextFieldState? = null,
        password: TextFieldState? = null,
        emailState: TextFieldState? = null,
        viewModel: UpdateProfileViewModel,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 10.dp, end = 10.dp)
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(50.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = AppStrings.update_profile.stringValue,
                modifier = Modifier.padding(10.dp),
                style = Typography.h5,
                color = textColorSemiBlack
            )
            Text(text = AppStrings.update_profile_description.stringValue)

            AppTextField(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 10.dp)
                    .height(55.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                value = userName?.text ?: "",
                hintLabel = AppStrings.Username.stringValue,
                onValueChanged = {
                    viewModel.setUiEvent(UpdateProfileUIEvent.EnteredName(value = it))
                }
            )

            viewModel.nameError.value?.let {
                Text(
                    text = viewModel.nameError?.value?.let { it } ?: "",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    style = TextStyle(
                        color = Color.Red
                    )
                )
            }
            AppTextField(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 10.dp)
                    .height(55.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = emailState?.text ?: "",
                hintLabel = AppStrings.Email.stringValue,
                onValueChanged = {
                    viewModel.setUiEvent(UpdateProfileUIEvent.EnteredEmail(value = it))
                }
            )
            viewModel.emailError.value?.let {
                Text(
                    text = viewModel.emailError?.value?.let { it } ?: "",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    style = TextStyle(
                        color = Color.Red
                    )
                )
            }

            AppTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 20.dp, end = 24.dp)
                    .clickable { },
                value = password?.text ?: "",
                visualTransformation = PasswordVisualTransformation(),
                hintLabel = "Password",
            ) {
                viewModel.setUiEvent(UpdateProfileUIEvent.EnteredPassword(value = it))
            }

            viewModel.passwordError.value?.let {
                Text(
                    text = viewModel.passwordError?.value?.let { it } ?: "",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    style = TextStyle(
                        color = Color.Red
                    )
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun UpdateProfileTopBar(navigator: Navigator?=null) {
    TopAppBar(
        modifier = Modifier.background(BOLD_SILVER_BACKGROUND_COLOR)
        ,
        title = { Text(text = "Update Profile") },
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

