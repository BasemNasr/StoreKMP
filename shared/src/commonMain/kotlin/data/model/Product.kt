package data.model


import data.model.response.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val category: Category,
    val creationAt: String,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Float,
    val title: String,
    val updatedAt: String,
    val rate: Double? = 4.5,
    val count: Int? = 49,
):BaseResponse()