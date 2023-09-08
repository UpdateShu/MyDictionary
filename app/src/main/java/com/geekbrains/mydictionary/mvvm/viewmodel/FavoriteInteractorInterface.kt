package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.mydictionary.mvvm.model.entities.Word

interface FavoriteInteractorInterface<T> {
    suspend fun getAllFavorite():List<Word>
    suspend fun setFavoriteData(word: Word)
    suspend fun deleteFavorite(idWord: Int)
}