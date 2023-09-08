package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.entities.Word
import com.geekbrains.repo.FavoriteRepositoryInterface

class FavoriteInteractor(private val favoriteRepository: com.geekbrains.repo.FavoriteRepositoryInterface<List<Word>>)
    : FavoriteInteractorInterface<com.geekbrains.entities.AppState>
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