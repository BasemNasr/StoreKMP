package data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val email: String,
    val username: String,
    val password: String,
    val name: RegisterName
){
    @Serializable
    data class RegisterName(
        val firstname: String,
        val lastname: String
    )
}
