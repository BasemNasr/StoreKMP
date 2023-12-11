package presentation.screens.category

import data.model.Category
import data.model.Product
import data.network.Resource
import domain.core.AppDataStore
import domain.usecase.CategoryUseCase
import domain.usecase.ProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent


class SelectedCategoryViewModel(
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {


    private val _products = MutableStateFlow<Resource<ArrayList<Product>>?>(null)
    val products: StateFlow<Resource<ArrayList<Product>>?> = _products

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is SelectedCategoryStateIntent.GetProducts -> {
                viewModelScope.launch {
                    _products.value = Resource.Loading
                    _products.value = productUseCase.getCategoryProducts(state.categoryId)
                }
            }
        }

    }
    override fun setUiEvent(state: AllStateEvent) {
    }

    fun clearStates() {
        viewModelScope.launch {
            _products.emit(null)
        }
    }
}


sealed class SelectedCategoryStateIntent : AllStateEvent() {
    data class GetProducts(val categoryId: Int) : SelectedCategoryStateIntent()
}



