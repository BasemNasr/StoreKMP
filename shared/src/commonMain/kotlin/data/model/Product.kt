package data.model


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
    val rating: Rating?
)