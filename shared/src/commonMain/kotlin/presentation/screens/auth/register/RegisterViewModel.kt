package presentation.screens.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.model.LoginResponse
import data.model.RegisterModel
import data.model.RegisterResponse
import data.model.TextFieldState
import data.network.Resource
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import utils.AppStrings


class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel() {

    private val _nameError: MutableStateFlow<String?> = MutableStateFlow(null)
    val nameError = _nameError.asStateFlow()

    private val _emailError: MutableStateFlow<String?> = MutableStateFlow(null)
    val emailError = _emailError.asStateFlow()

    private val _passwordError: MutableStateFlow<String?> = MutableStateFlow(null)
    val passwordError = _passwordError.asStateFlow()

    private val _nameState = mutableStateOf(
        TextFieldState(
            text = "",
            hint = "Enter your Name",
            isHintVisible = false,
        )
    )
    val userName: State<TextFieldState> = _nameState

    private val _emailState = mutableStateOf(
        TextFieldState(
            text = "",
            hint = "Enter your valid email",
            isHintVisible = false,
        )
    )
    val email: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(
        TextFieldState(
            text = "",
            hint = "Enter your valid password",
            isHintVisible = false,
        )
    )
    val password: State<TextFieldState> = _passwordState
    

    private val _register = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val register: StateFlow<Resource<RegisterResponse>?> = _register


    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is RegisterStateIntent.Register -> {
                viewModelScope.launch {
                    if (userName.value.text.length >= 5 && password.value.text.length >= 6 && email.value.text.length >= 6) {
                        _register.value = Resource.Loading
                        _register.value = registerUseCase.invoke(
                            RegisterModel(
                                email=email.value.text,
                                name = userName.value.text,
                                password = password.value.text,
                                avatar = "https://api.lorem.space/image/face?w=640&h=480",
                            )
                        )
                    }
                }
            }

        }

    }

    override fun setUiEvent(state: AllStateEvent) {
        when (state) {
            is RegisterUIEvent.EnteredName -> {
                _nameState.value = userName.value.copy(
                    text = state.value
                )
                viewModelScope.launch {
                    if (userName.value.text.isEmpty()) {
                        _nameError.emit(AppStrings.user_name_validation.stringValue)
                    } else {
                        _nameError.emit(null)
                    }
                }
            }

            is RegisterUIEvent.EnteredPassword -> {
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
            is RegisterUIEvent.EnteredEmail -> {
                _emailState.value = email.value.copy(
                    text = state.value
                )
                viewModelScope.launch {
                    if (email.value.text.length < 6) {
                        _emailError.emit(AppStrings.email_validation.stringValue)
                    } else {
                        _emailError.emit(null)
                    }
                }
            }
        }
    }

    fun clearAllState() {
        viewModelScope.launch {
            _register.emit(null)
        }
    }
}



sealed class RegisterStateIntent : AllStateEvent() {
    object  Register : RegisterStateIntent()
}

sealed class RegisterUIEvent : AllStateEvent() {
    data class EnteredName(val value: String) : RegisterUIEvent()
    data class EnteredEmail(val value: String) : RegisterUIEvent()
    data class EnteredPassword(val value: String) : RegisterUIEvent()
}


