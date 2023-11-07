package presentation.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.bn.store.kmp.MR
import data.model.LoginResponse
import data.model.RegisterModel
import data.model.RegisterResponse
import data.network.Resource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import utils.CommonUtil


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel() {

    private val _emailError: MutableStateFlow<StringResource?> = MutableStateFlow(null)
    val emailError = _emailError.asStateFlow()

    private val _passwordError: MutableStateFlow<StringResource?> = MutableStateFlow(null)
    val passwordError = _passwordError.asStateFlow()

    private val _emailState = mutableStateOf(
        TextFieldState(
            text = "basem@gmail.com",
            hint = "Enter your email",
            isHintVisible = false,
        )
    )
    val email: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(
        TextFieldState(
            text = "Basem123456",
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
                    _login.value = Resource.Loading
                    _login.value = loginUseCase.invoke(email.value.text, password.value.text)
//                    if(_passwordError.value !=null && _emailError.value!=null){
//                        _login.value = Resource.Loading
//                        _login.value = loginUseCase.invoke(email.value.text, password.value.text)
//                    }
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
        when(state){
            is LoginUIStateEvent.EnteredEmail->{
                viewModelScope.launch {
                    _emailState.value = email.value.copy(
                        text = state.value
                    )
                    if(email.value.text.isEmpty()){
                        _emailError.emit(MR.strings.email_validation)
                    }else{
                        _emailError.emit(null)
                    }
                }
            }
            is LoginUIStateEvent.EnteredPassword->{
                viewModelScope.launch {
                    _passwordState.value = password.value.copy(
                        text = state.value
                    )
                    if(password.value.text.length<10){
                        _passwordError.emit(MR.strings.password_validation)
                    }else{
                        _passwordError.emit(null)
                    }
                }

            }
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
    data class EnteredEmail(val value: String) : LoginUIStateEvent()
    data class EnteredPassword(val value: String) : LoginUIStateEvent()
}


