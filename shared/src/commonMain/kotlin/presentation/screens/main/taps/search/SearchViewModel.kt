package presentation.screens.main.taps.search

import data.model.Product
import data.network.Resource
import domain.usecase.ProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.base.BaseViewModel.AllStateEvent


class SearchViewModel(
    private val productUseCase: ProductUseCase,
) : BaseViewModel() {


    private val _products = MutableStateFlow<Resource<ArrayList<Product>>?>(null)
    val products: StateFlow<Resource<ArrayList<Product>>?> = _products


    init {
        setStateEvent(SearchStateIntent.GetAllProducts)
    }

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is SearchStateIntent.GetAllProducts -> {
                viewModelScope.launch {
                    _products.value = Resource.Loading
                    _products.value = productUseCase.getAllProducts()
                }
            }
        }

    }

    override fun setUiEvent(state: AllStateEvent) {
    }

    fun clearHomeStates() {
        viewModelScope.launch {
            _products.emit(null)
        }
    }
}


sealed class SearchStateIntent : AllStateEvent() {
    object GetAllProducts : SearchStateIntent()
}



