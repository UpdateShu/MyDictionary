package com.geekbrains.mydictionary.mvvm.model.repo

import com.geekbrains.mydictionary.mvvm.model.entities.Word

interface RepositoryInterface<T : Any> {
    suspend fun getDataRepository(word: String): T
    suspend fun setDataLocal(words: List<Word>)
}