package presentation.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import data.model.LoginResponse
import data.model.RegisterResponse
import data.model.TextFieldState
import data.network.Resource
import data.repository.AppPreferences
import data.repository.AppPreferencesRepository
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


class MainViewModel(
    private val appDataStoreManager: AppDataStore,
    ) : BaseViewModel() {

    private val _userToken = mutableStateOf(String())
    val userToken: State<String> = _userToken

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is MainStateIntent.GettingToken -> {
                viewModelScope.launch {
                    val token = appDataStoreManager.readValue(DataStoreKeys.TOKEN)
                    _userToken.value = token?:""
                }
            }
        }

    }

    override fun setUiEvent(state: AllStateEvent) {
    }

}


sealed class MainStateIntent : AllStateEvent() {
    object GettingToken : MainStateIntent()
}



