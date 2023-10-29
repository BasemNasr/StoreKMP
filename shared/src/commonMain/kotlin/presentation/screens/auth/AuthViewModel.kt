package presentation.screens.auth

import data.model.LoginResponse
import data.model.RegisterModel
import data.model.RegisterResponse
import data.network.Resource
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent


class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel() {

    private val _login = MutableStateFlow<Resource<LoginResponse>?>(null)
    val login: StateFlow<Resource<LoginResponse>?> = _login

    private val _register = MutableStateFlow<Resource<RegisterResponse>?>(null)
    val register: StateFlow<Resource<RegisterResponse>?> = _register

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is AuthStateIntent.Login -> {
                viewModelScope.launch {
                    _login.value = Resource.Loading
                    _login.value = loginUseCase.invoke(state.userName, state.password)
                }
            }

            is AuthStateIntent.Register -> {
                viewModelScope.launch {
                    _register.value = Resource.Loading
                    _register.value = registerUseCase.invoke(state.registerModel)
                }
            }
        }

    }
}

sealed class AuthStateIntent : AllStateEvent() {
    data class Login(val userName: String, val password: String) : AuthStateIntent()
    data class Register(val registerModel: RegisterModel) : AuthStateIntent()
}


