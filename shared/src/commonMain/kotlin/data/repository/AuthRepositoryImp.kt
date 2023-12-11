package data.repository

import data.core.AppDataStoreManager
import data.model.request.LoginRequest
import data.model.response.LoginResponse
import data.model.request.RegisterModel
import data.model.response.RegisterResponse
import data.network.Resource
import data.network.Urls
import domain.core.AppDataStore
import domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import presentation.base.DataStoreKeys.TOKEN

class AuthRepositoryImp(private val httpClient: HttpClient,    private val appDataStoreManager: AppDataStore,
) : AuthRepository {
    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val response = httpClient.post(Urls.LOGIN) {
            setBody(LoginRequest(email,password))
        }.body<LoginResponse>()
        val isFailed = response.message != null
        return if(!isFailed){
            try {
                Resource.Success(
                    response
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e)
            }
        }else{
            Resource.Failure(Exception(response.message))
        }

    }

    override suspend fun register(registerModel: RegisterModel): Resource<RegisterResponse> {
        val response = httpClient.post(Urls.REGISTER) {
            setBody(registerModel)
        }.body<RegisterResponse>()
        return try {
            if(response.message.isNullOrEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception(response.message[0]))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProfile() :Resource<RegisterResponse> {
        val token = appDataStoreManager.readValue(TOKEN)
        val response = httpClient.get(Urls.GET_PROFILE_DATA) {
            header("Authorization","Bearer $token")
        }.body<RegisterResponse>()
        return try {
            if(response.message.isNullOrEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception(response.message[0]))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


    override suspend fun updateProfile(registerModel: RegisterModel):Resource<RegisterResponse> {
        val response = httpClient.put(Urls.UPDATE_PROFILE+registerModel.id) {
        }.body<RegisterResponse>()
        return try {
            if(response.message.isNullOrEmpty()){
                Resource.Success(
                    response
                )
            }else{
                Resource.Failure(Exception(response.message[0]))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}