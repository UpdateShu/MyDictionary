package com.geekbrains.viewmodel

interface FavoriteInteractorInterface<T> {
    suspend fun getAllFavorite():List<com.geekbrains.entities.Word>
    suspend fun setFavoriteData(word: com.geekbrains.entities.Word)
    suspend fun deleteFavorite(idWord: Int)
}