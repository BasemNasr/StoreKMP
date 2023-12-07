package presentation.screens.splash

import domain.core.AppDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent
import presentation.base.DataStoreKeys


class SplashViewModel(
    private val appDataStoreManager: AppDataStore,
    ) : BaseViewModel() {

    private val _isLogin = MutableStateFlow<Boolean?>(null)
    val isLogin: StateFlow<Boolean?> = _isLogin.asStateFlow()


    init {
        viewModelScope.launch {
            val token = appDataStoreManager.readValue(DataStoreKeys.TOKEN)?:""
            _isLogin.value = token.isNotBlank()
        }
    }

    override fun setStateEvent(state: AllStateEvent) {
    }

    override fun setUiEvent(state: AllStateEvent) {
    }

}


sealed class MainStateIntent : AllStateEvent() {
    object GettingToken : MainStateIntent()
}



