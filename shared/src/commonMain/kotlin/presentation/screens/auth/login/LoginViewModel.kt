package presentation.screens.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.model.response.LoginResponse
import data.model.response.RegisterResponse
import data.model.TextFieldState
import data.network.Resource
import domain.core.AppDataStore
import domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import presentation.base.DataStoreKeys
import utils.AppStrings


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val appDataStoreManager: AppDataStore,
    ) : BaseViewModel() {

    private val _userNameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val nameError = _userNameError.asStateFlow()

    private val _passwordError: MutableStateFlow<String?> = MutableStateFlow(null)
    val passwordError = _passwordError.asStateFlow()

    private val _userNameState = mutableStateOf(
        TextFieldState(
            text = "john@mail.com",
            hint = "Enter your Email",
            isHintVisible = false,
        )
    )
    val userName: State<TextFieldState> = _userNameState

    private val _passwordState = mutableStateOf(
        TextFieldState(
            text = "changeme",
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
            is LoginStateIntent.SaveToken -> {
                viewModelScope.launch {
                    appDataStoreManager.setValue(DataStoreKeys.TOKEN,state.token)
                }
            }
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
                        _userNameError.emit(AppStrings.user_name_validation.stringValue)
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
                        _passwordError.emit(AppStrings.PasswordValidation.stringValue)
                    } else {
                        _passwordError.emit(null)
                    }
                }

            }
        }
    }

    fun clearLoginState() {
        viewModelScope.launch {
            _login.emit(null)
        }
    }
}


sealed class LoginStateIntent : AllStateEvent() {
    object Login : LoginStateIntent()
    data class SaveToken(val token: String) : LoginStateIntent()
//    data class Register(val registerModel: RegisterModel) : AuthStateIntent()
}

sealed class LoginUIStateEvent : AllStateEvent() {
    data class EnteredUserName(val value: String) : LoginUIStateEvent()
    data class EnteredPassword(val value: String) : LoginUIStateEvent()
}


