package data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val access_token: String?=null,
    val refresh_token: String?=null,
):BaseResponse()