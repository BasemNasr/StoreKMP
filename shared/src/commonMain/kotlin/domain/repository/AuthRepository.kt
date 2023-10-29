package domain.repository

import data.model.LoginResponse
import data.model.RegisterModel
import data.model.RegisterResponse
import data.network.Resource

interface AuthRepository {
    suspend fun login(userName: String, password: String): Resource<LoginResponse>
    suspend fun register(registerModel: RegisterModel): Resource<RegisterResponse>
}