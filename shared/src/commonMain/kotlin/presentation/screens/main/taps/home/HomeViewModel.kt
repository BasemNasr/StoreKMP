package presentation.screens.main.taps.home

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


class HomeViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val productUseCase: ProductUseCase,
    private val appDataStoreManager: AppDataStore,
) : BaseViewModel() {


    private val _categories = MutableStateFlow<Resource<ArrayList<Category>>?>(null)
    val categories: StateFlow<Resource<ArrayList<Category>>?> = _categories

    private val _products = MutableStateFlow<Resource<ArrayList<Product>>?>(null)
    val products: StateFlow<Resource<ArrayList<Product>>?> = _products


    init {
        setStateEvent(HomeStateIntent.GetCategories)
    }

    override fun setStateEvent(state: AllStateEvent) {
        when (state) {
            is HomeStateIntent.GetCategories -> {
                viewModelScope.launch {
                    _categories.value = Resource.Loading
                    _categories.value = categoryUseCase.invoke()
                    when(_categories.value){
                        is Resource.Failure -> {}
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            _products.value = productUseCase.getCategoryProducts(
                                (_categories.value as Resource.Success<ArrayList<Category>>).result[0].id ?:0)
                        }
                        null -> {}
                    }
                }
            }

            is HomeStateIntent.SelectCategory -> {
                viewModelScope.launch {
                    _products.value = Resource.Loading
                    _products.value = productUseCase.getCategoryProducts(state.categoryId)
                }
            }
        }

    }
    override fun setUiEvent(state: AllStateEvent) {
    }

    fun clearHomeStates() {
        viewModelScope.launch {
            _categories.emit(null)
            _products.emit(null)
        }
    }
}


sealed class HomeStateIntent : AllStateEvent() {
    object GetCategories : HomeStateIntent()
    data class SelectCategory(val categoryId: Int) : HomeStateIntent()
}



