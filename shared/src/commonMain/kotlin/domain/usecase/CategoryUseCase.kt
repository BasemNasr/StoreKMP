package domain.usecase

import domain.repository.HomeRepository


class CategoryUseCase
constructor(
    private val repo: HomeRepository,
) {
    suspend fun invoke() = repo.getCategories()
}