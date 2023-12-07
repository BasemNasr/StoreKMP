package data.model

import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val creationAt: String?=null,
    val id: Int?=null,
    val image: String?=null,
    val name: String?=null,
    val updatedAt: String?=null
)