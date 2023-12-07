package domain.repository

import data.model.Category
import data.model.Product
import data.model.response.LoginResponse
import data.model.request.RegisterModel
import data.model.response.RegisterResponse
import data.network.Resource

interface HomeRepository {
    suspend fun getAllProducts(): Resource<ArrayList<Product>>
    suspend fun getProductDetails(productId:Int): Resource<Product>
    suspend fun getCategories(): Resource<ArrayList<Category>>
    suspend fun getCategoryProducts(catId:Int): Resource<ArrayList<Product>>
}