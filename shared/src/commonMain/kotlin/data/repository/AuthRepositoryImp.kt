package data.repository

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

class AuthRepositoryImp(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun login(userName: String, password: String): Resource<LoginResponse> {
        val response = httpClient.get(Urls.LOGIN) {
            parameter("username", "$userName")
            parameter("password", "$password")

        }.body<LoginResponse>()
        return try {
            Resource.Success(
                response
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
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