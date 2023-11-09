package data.model

import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val creationAt: String,
    val id: Int,
    val image: String,
    val name: String,
    val updatedAt: String
)