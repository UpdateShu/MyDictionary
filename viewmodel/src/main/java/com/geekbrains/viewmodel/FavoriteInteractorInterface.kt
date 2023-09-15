package com.geekbrains.viewmodel

import com.geekbrains.entities.Word

interface FavoriteInteractorInterface<T> {
    suspend fun getAllFavorite(): List<Word>
    suspend fun setFavoriteData(word: Word)
    suspend fun deleteFavorite(idWord: Int)
}