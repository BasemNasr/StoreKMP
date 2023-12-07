package domain.usecase

import data.model.request.RegisterModel
import domain.repository.AuthRepository
import domain.repository.HomeRepository


class ProductUseCase
constructor(
    private val repo: HomeRepository,
) {
    suspend fun getProductDetailsUseCase(productId:Int) = repo.getProductDetails(productId)
    suspend fun getAllProducts() = repo.getAllProducts()
    suspend fun getCategoryProducts(categoryId:Int) = repo.getCategoryProducts(categoryId)
}