package presentation.screens.main.taps.profile

import data.model.LoginResponse
import data.network.Resource
import domain.core.AppDataStore
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import presentation.base.DataStoreKeys


class ProfileViewModel(
    private val appDataStoreManager: AppDataStore,
    ) : BaseViewModel() {


    private val _logout = MutableStateFlow<Boolean?>(null)
    val logout: StateFlow<Boolean?> = _logout
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

}


