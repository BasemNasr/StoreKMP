package presentation.screens.main.taps.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.model.TextFieldState
import data.model.response.RegisterResponse
import data.network.Resource
import domain.core.AppDataStore
import domain.usecase.GetProfileUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import presentation.base.DataStoreKeys
import presentation.screens.auth.updateProfile.UpdateProfileStateIntent
import presentation.screens.auth.updateProfile.UpdateProfileUIEvent


class ProfileViewModel(
    private val appDataStoreManager: AppDataStore,
    private val getProfileUseCase: GetProfileUseCase,
    ) : BaseViewModel() {


    private val _logout = MutableStateFlow<Boolean?>(null)
    val logout: StateFlow<Boolean?> = _logout

    private var userProfile:RegisterResponse?=null

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


    init {
        setStateEvent(ProfileStateIntent.GettingProfile)
    }
    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is ProfileStateIntent.LogoutUser -> {
                viewModelScope.launch {
                    async {
                        appDataStoreManager.setValue(DataStoreKeys.TOKEN,"")
                    }.await()
                    _logout.emit(true)


                }
            }
            is ProfileStateIntent.GettingProfile -> {
                viewModelScope.launch {
                    val response = getProfileUseCase.invoke()
                    when(getProfileUseCase.invoke()){
                        is Resource.Failure -> {}
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            userProfile = (response as Resource.Success<RegisterResponse>).result
                            userProfile?.let {
                                _nameState.value = userName.value.copy(
                                    text = userProfile?.name?:""
                                )
                                _emailState.value = email.value.copy(
                                    text = userProfile?.email?:""
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    override fun setUiEvent(state: AllStateEvent) {
    }

    override fun onCleared() {
        super.onCleared()
//        viewModelScope.launch {
//            _login.emit(null)
//        }
    }
}



sealed class ProfileStateIntent : AllStateEvent() {
    object LogoutUser : ProfileStateIntent()
    object  GettingProfile : ProfileStateIntent()


}


