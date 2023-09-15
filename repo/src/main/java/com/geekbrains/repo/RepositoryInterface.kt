package com.geekbrains.repo

import com.geekbrains.entities.Word

interface RepositoryInterface<T> {
    suspend fun getDataRepository(word: String): List<Word>
    suspend fun setDataLocal(words: List<Word>)
    suspend fun setDataFavorite(words: Word)
    suspend fun getDataFavorite(): List<Word>
}