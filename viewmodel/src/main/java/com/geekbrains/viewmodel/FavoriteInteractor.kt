package com.geekbrains.viewmodel

import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word

class FavoriteInteractor(private val favoriteRepository: com.geekbrains.repo.FavoriteRepositoryInterface<List<Word>>)
    : FavoriteInteractorInterface<AppState>
{
    override suspend fun getAllFavorite(): List<com.geekbrains.entities.Word> {
        return favoriteRepository.getAllFavorite()
    }

    override suspend fun deleteFavorite(idWord: Int) {
        favoriteRepository.deleteFavorite(idWord)
    }

    override suspend fun setFavoriteData(word: com.geekbrains.entities.Word) {
        TODO("Not yet implemented")
    }
}