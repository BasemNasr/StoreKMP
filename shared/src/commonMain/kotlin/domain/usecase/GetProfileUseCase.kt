package domain.usecase

import data.model.request.RegisterModel
import domain.repository.AuthRepository


class GetProfileUseCase
constructor(
    private val repo: AuthRepository,
) {
    suspend fun invoke() = repo.getProfile()
}