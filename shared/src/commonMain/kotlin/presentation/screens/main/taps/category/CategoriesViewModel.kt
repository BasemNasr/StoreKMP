package presentation.screens.main.taps.category

import data.model.Category
import data.network.Resource
import domain.usecase.CategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent


class CategoriesViewModel(
    private val categoryUseCase: CategoryUseCase,
) : BaseViewModel() {


    private val _categories = MutableStateFlow<Resource<ArrayList<Category>>?>(null)
    val categories: StateFlow<Resource<ArrayList<Category>>?> = _categories

    init {
        setStateEvent(CategoriesStateIntent.GetCategories)
    }

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is CategoriesStateIntent.GetCategories -> {
                viewModelScope.launch {
                    _categories.value = Resource.Loading
                    _categories.value = categoryUseCase.invoke()
                }
            }
        }

    }

    override fun setUiEvent(state: AllStateEvent) {
    }

    fun clearCategoriesStates() {
        viewModelScope.launch {
            _categories.emit(null)
        }
    }
}


sealed class CategoriesStateIntent : AllStateEvent() {
    object GetCategories : CategoriesStateIntent()
}



