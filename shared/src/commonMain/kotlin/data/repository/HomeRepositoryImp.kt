package data.repository

import data.model.Category
import data.model.Product
import data.model.request.LoginRequest
import data.model.response.LoginResponse
import data.model.request.RegisterModel
import data.model.response.RegisterResponse
import data.network.Resource
import data.network.Urls
import data.network.Urls.CATEGORIES
import data.network.Urls.GET_CATEGORY_PRODUCTS
import domain.repository.AuthRepository
import domain.repository.HomeRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class HomeRepositoryImp(private val httpClient: HttpClient) : HomeRepository {
    override suspend fun getAllProducts(): Resource<ArrayList<Product>> {
        val response = httpClient.get(Urls.GET_ALL_PRODUCTS) {
        }.body<ArrayList<Product>>()
        return try {
            if(!response.isEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception("Empty Products"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProductDetails(productId:Int): Resource<Product> {
        val response = httpClient.get(Urls.PRODUCT_DETAILS+"/$productId") {
        }.body<Product>()
        return try {
            if(response.message.isNullOrEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception("Empty Products"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getCategories(): Resource<ArrayList<Category>> {
        val response = httpClient.get(CATEGORIES) {

        }.body<ArrayList<Category>>()
        return try {
            if(!response.isEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception("Empty Categories"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    override suspend fun getCategoryProducts(catId: Int): Resource<ArrayList<Product>> {
        val response = httpClient.get("$CATEGORIES/$catId/$GET_CATEGORY_PRODUCTS") {

        }.body<ArrayList<Product>>()
        return try {
            if(!response.isEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception("Empty Products"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }
}