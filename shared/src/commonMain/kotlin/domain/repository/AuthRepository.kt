package domain.repository

import data.model.response.LoginResponse
import data.model.request.RegisterModel
import data.model.response.RegisterResponse
import data.network.Resource

interface AuthRepository {
    suspend fun login(userName: String, password: String): Resource<LoginResponse>
    suspend fun register(registerModel: RegisterModel): Resource<RegisterResponse>
    suspend fun getProfile(): Resource<RegisterResponse>
    suspend fun updateProfile(registerModel: RegisterModel): Resource<RegisterResponse>
}