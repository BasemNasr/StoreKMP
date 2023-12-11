package presentation.screens.auth.updateProfile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.model.Category
import data.model.request.RegisterModel
import data.model.response.RegisterResponse
import data.model.TextFieldState
import data.network.Resource
import domain.usecase.GetProfileUseCase
import domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import utils.AppStrings


class UpdateProfileViewModel(
    private val registerUseCase: RegisterUseCase,
    private val getProfileUseCase: GetProfileUseCase,
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
    

    private var userProfile:RegisterResponse?=null


    private val _updateProfile = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val updateProfile: StateFlow<Resource<RegisterResponse>?> = _updateProfile

    init {
        setStateEvent(UpdateProfileStateIntent.GettingProfile)
    }

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is UpdateProfileStateIntent.GettingProfile -> {
                viewModelScope.launch {
                    val response = getProfileUseCase.invoke()
                    when(getProfileUseCase.invoke()){
                        is Resource.Failure -> {}
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            userProfile = (response as Resource.Success<RegisterResponse>).result
                            userProfile?.let {
                                setUiEvent(UpdateProfileUIEvent.EnteredName(userProfile?.name?:""))
                                setUiEvent(UpdateProfileUIEvent.EnteredEmail(userProfile?.email?:""))
                                setUiEvent(UpdateProfileUIEvent.EnteredPassword(userProfile?.password?:""))
                            }
                        }
                    }
                }
            }
            is UpdateProfileStateIntent.UpdateProfile -> {
                viewModelScope.launch {
                    if (userName.value.text.length >= 5 && password.value.text.length >= 6 && email.value.text.length >= 6) {
                        _updateProfile.value = Resource.Loading
                        _updateProfile.value = registerUseCase.invoke(
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
            is UpdateProfileUIEvent.EnteredName -> {
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

            is UpdateProfileUIEvent.EnteredPassword -> {
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
            is UpdateProfileUIEvent.EnteredEmail -> {
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
            _updateProfile.emit(null)
        }
    }
}



sealed class UpdateProfileStateIntent : AllStateEvent() {
    object  GettingProfile : UpdateProfileStateIntent()
    object  UpdateProfile : UpdateProfileStateIntent()
}

sealed class UpdateProfileUIEvent : AllStateEvent() {
    data class EnteredName(val value: String) : UpdateProfileUIEvent()
    data class EnteredEmail(val value: String) : UpdateProfileUIEvent()
    data class EnteredPassword(val value: String) : UpdateProfileUIEvent()
}


