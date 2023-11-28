package data.model

data class RegisterResponse(
    val avatar: String? = null,
    val creationAt: String? = null,
    val email: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val password: String? = null,
    val role: String? = null,
    val updatedAt: String? = null,
    val error: String? = null,
    val message: List<String>? = null,
    val statusCode: Int? = null
)


