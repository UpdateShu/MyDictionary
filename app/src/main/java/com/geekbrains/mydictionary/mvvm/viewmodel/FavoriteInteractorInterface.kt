package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.entities.Word

interface FavoriteInteractorInterface<T> {
    suspend fun getAllFavorite():List<com.geekbrains.entities.Word>
    suspend fun setFavoriteData(word: com.geekbrains.entities.Word)
    suspend fun deleteFavorite(idWord: Int)
}