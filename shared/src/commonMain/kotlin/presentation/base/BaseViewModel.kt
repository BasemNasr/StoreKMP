package presentation.base

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected lateinit var launchIn: Job
    protected lateinit var secondIn: Job


    val handlerException by lazy {
        CoroutineExceptionHandler { _, ex ->
            viewModelScope.launch {

            }
        }
    }

    abstract fun setStateEvent(state: AllStateEvent)
    open class AllStateEvent {
    }


    override fun onCleared() {
        super.onCleared()
        if (::launchIn.isInitialized) launchIn.cancel()
        if (::secondIn.isInitialized) secondIn.cancel()
    }
}