package data.model.response

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    val statusCode: Int?=null,
    val message: String?=null
)