package presentation.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.bn.store.kmp.MR
import data.model.LoginResponse
import data.model.RegisterResponse
import data.network.Resource
import dev.icerock.moko.resources.StringResource
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel() {

    private val _userNameError: MutableStateFlow<StringResource?> = MutableStateFlow(null)
    val nameError = _userNameError.asStateFlow()

    private val _passwordError: MutableStateFlow<StringResource?> = MutableStateFlow(null)
    val passwordError = _passwordError.asStateFlow()

    private val _userNameState = mutableStateOf(
        TextFieldState(
            text = "johnd",
            hint = "Enter your User Name",
            isHintVisible = false,
        )
    )
    val userName: State<TextFieldState> = _userNameState

    private val _passwordState = mutableStateOf(
        TextFieldState(
            text = "m38rmF$",
            hint = "Enter your password",
            isHintVisible = false,
        )
    )
    val password: State<TextFieldState> = _passwordState

    private val _login = MutableStateFlow<Resource<LoginResponse>?>(null)
    val login: StateFlow<Resource<LoginResponse>?> = _login

    private val _register = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val register: StateFlow<Resource<RegisterResponse>?> = _register


    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is LoginStateIntent.Login -> {
                viewModelScope.launch {
                    if (userName.value.text.length >= 5 && password.value.text.length >= 6) {
                        _login.value = Resource.Loading
                        _login.value = loginUseCase.invoke(userName.value.text, password.value.text)
                    }
                }
            }

//            is LoginStateIntent.Register -> {
//                viewModelScope.launch {
//                    _register.value = Resource.Loading
//                    _register.value = registerUseCase.invoke(state.registerModel)
//                }
//            }
        }

    }

    override fun setUiEvent(state: AllStateEvent) {
        when (state) {
            is LoginUIStateEvent.EnteredUserName -> {
                _userNameState.value = userName.value.copy(
                    text = state.value
                )
                viewModelScope.launch {
                    if (userName.value.text.isEmpty()) {
                        _userNameError.emit(MR.strings.user_name_validation)
                    } else {
                        _userNameError.emit(null)
                    }
                }
            }

            is LoginUIStateEvent.EnteredPassword -> {
                _passwordState.value = password.value.copy(
                    text = state.value
                )
                viewModelScope.launch {
                    if (password.value.text.length < 6) {
                        _passwordError.emit(MR.strings.password_validation)
                    } else {
                        _passwordError.emit(null)
                    }
                }

            }
        }
    }

    fun clearLoginState(){
        viewModelScope.launch {
            _login.emit(null)
        }
    }
}


data class TextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)

sealed class LoginStateIntent : AllStateEvent() {
    object Login : LoginStateIntent()
//    data class Register(val registerModel: RegisterModel) : AuthStateIntent()
}

sealed class LoginUIStateEvent : AllStateEvent() {
    data class EnteredUserName(val value: String) : LoginUIStateEvent()
    data class EnteredPassword(val value: String) : LoginUIStateEvent()
}


