package domain.usecase

import domain.repository.AuthRepository


class LoginUseCase
constructor(
    private val repo: AuthRepository,
) {
    suspend fun invoke(username:String,password:String) = repo.login(username,password)
}