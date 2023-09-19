package com.geekbrains.viewmodel

import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word
import com.geekbrains.repo.FavoriteRepositoryInterface

class FavoriteInteractor(private val favoriteRepository: FavoriteRepositoryInterface<List<Word>>)
    : FavoriteInteractorInterface<AppState>
{

    override suspend fun getAllFavorite() = favoriteRepository.getAllFavorite()

    override suspend fun deleteFavorite(idWord: Int) {
        favoriteRepository.deleteFavorite(idWord)
    }

    override suspend fun setFavoriteData(word: Word) {
        TODO("Not yet implemented")
    }
}