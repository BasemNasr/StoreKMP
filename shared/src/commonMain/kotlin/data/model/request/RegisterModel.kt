package data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val email: String,
    val password: String,
    val name: String,
    val avatar: String,
    val id: Int?=null,
)