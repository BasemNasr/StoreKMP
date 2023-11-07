package data.repository

import data.model.LoginRequest
import data.model.LoginResponse
import data.model.RegisterModel
import data.model.RegisterResponse
import data.network.Resource
import data.network.Urls
import domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.encodeToString
import utils.CommonUtil.jsonToObject

class AuthRepositoryImp(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun login(userName: String, password: String): Resource<LoginResponse> {
        val response = httpClient.post(Urls.LOGIN) {
            setBody(LoginRequest(userName,password))
        }.body<String>()
        val isFailed = response.startsWith("{")
        return if(!isFailed){
            try {
                Resource.Success(
                    jsonToObject(response)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e)
            }
        }else{
            Resource.Failure(Exception(response))
        }

    }

    override suspend fun register(registerModel: RegisterModel): Resource<RegisterResponse> {
        val response = httpClient.get(Urls.LOGIN) {
            parameter("username", "${registerModel.username}")
            parameter("email", "${registerModel.email}")
            parameter("password", "${registerModel.password}")
            parameter("name", "${registerModel.name}")
        }.body<RegisterResponse>()
        return try {
            Resource.Success(
                response
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}