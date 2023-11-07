package presentation.screens.auth

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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.bn.store.kmp.MR
import dev.icerock.moko.resources.compose.*
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.components.AppPrimaryButton
import presentation.components.AppTextField
import presentation.theme.BOLD_SILVER_BACKGROUND_COLOR
import presentation.theme.DarkPurple
import presentation.theme.Typography
import presentation.theme.gray2
import presentation.theme.textColorSemiBlack
import presentation.theme.yellow

class LoginScreen(
) : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        loginContent()
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun loginContent(
        viewModel: LoginViewModel = koinInject()
    ) {


        val emailState = viewModel?.email?.value
        val passwordState = viewModel?.password?.value

        val loginState = viewModel?.login?.collectAsState()

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

                BoxLoginDataInputs(emailState, passwordState,viewModel)

                Spacer(modifier = Modifier.height(40.dp))

                AppPrimaryButton(
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 12.dp)
                        .width(400.dp)
                        .height(90.dp),
                    buttonText = stringResource(MR.strings.login),
                    isLoading = loginState?.value is data.network.Resource.Loading,
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
                    viewModel.setStateEvent(LoginStateIntent.Login)
                }



                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 6.dp, bottom = 40.dp, end = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(MR.strings.have_not_account),
                        modifier = Modifier.padding(start = 2.dp, end = 2.dp, top = 2.dp),
                        style = TextStyle(fontSize = 14.sp),
                        color = gray2,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(MR.strings.regiser_new_account),
                        modifier = Modifier
                            .padding(start = 2.dp, end = 2.dp)
                            .clickable {
//                                navController?.navigate(AppScreens.RegisterScreen.name)
                            },
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                        color = textColorSemiBlack,
                        textAlign = TextAlign.Center,

                        )


                }

            }
        }


        when(loginState?.value){
            is data.network.Resource.Success -> {

            }
            is data.network.Resource.Failure -> {

            }
            data.network.Resource.Loading -> {

            }
            null -> {}
        }
    }

    @Composable
    private fun BoxLoginDataInputs(
        email: TextFieldState? = null,
        password: TextFieldState? = null,
        viewModel: LoginViewModel,
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
                text = stringResource(MR.strings.login),
                modifier = Modifier.padding(10.dp),
                style = Typography.h5,
                color = textColorSemiBlack
            )
            Text(text = stringResource(MR.strings.login_description))

            AppTextField(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 10.dp)
                    .height(55.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = email?.text ?: "",
                hintLabel = stringResource(MR.strings.email),
                onValueChanged = {
                    viewModel.setUiEvent(LoginUIStateEvent.EnteredEmail(value = it))
                }
            )

            viewModel.emailError.value?.let {
                Text(
                    text = viewModel.emailError?.value?.let { stringResource(it) }?:"",
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
                    .clickable { }
                    .testTag("PasswordTextField"),
                value = password?.text ?: "",
                visualTransformation = PasswordVisualTransformation(),
                hintLabel = stringResource(MR.strings.password),
            ) {
                viewModel.setUiEvent(LoginUIStateEvent.EnteredPassword(value = it))
            }
            viewModel.passwordError.value?.let {
                Text(
                    text = viewModel.passwordError?.value?.let { stringResource(it) }?:"",
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
