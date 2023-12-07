package domain.usecase

import data.model.request.RegisterModel
import domain.repository.AuthRepository


class RegisterUseCase
constructor(
    private val repo: AuthRepository,
) {
    suspend fun invoke(registerModel: RegisterModel) = repo.register(registerModel)
}