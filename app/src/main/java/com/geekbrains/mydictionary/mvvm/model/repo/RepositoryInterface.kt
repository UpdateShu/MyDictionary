package com.geekbrains.mydictionary.mvvm.model.repo

import com.geekbrains.mydictionary.mvvm.model.entities.Word

interface RepositoryInterface<T> {
    suspend fun getDataRepository(word: String): List<Word>
    suspend fun setDataLocal(words: List<Word>)
    suspend fun setDataFavorite(words: Word)
    suspend fun getDataFavorite(): List<Word>
}