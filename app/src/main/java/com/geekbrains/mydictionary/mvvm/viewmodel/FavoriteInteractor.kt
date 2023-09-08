package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.repo.FavoriteRepositoryInterface

class FavoriteInteractor(private val favoriteRepository: FavoriteRepositoryInterface<List<Word>>)
    : FavoriteInteractorInterface<AppState>
{
    override suspend fun getAllFavorite(): List<Word> {
        return favoriteRepository.getAllFavorite()
    }

    override suspend fun deleteFavorite(idWord: Int) {
        favoriteRepository.deleteFavorite(idWord)
    }

    override suspend fun setFavoriteData(word: Word) {
        TODO("Not yet implemented")
    }
}