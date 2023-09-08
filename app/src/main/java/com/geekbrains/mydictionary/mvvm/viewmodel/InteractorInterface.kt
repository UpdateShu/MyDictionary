package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.entities.Word

interface InteractorInterface<T : Any> {
    suspend fun getDataInteractor(word: String, fromRemoteSource: Boolean): List<Word>
    suspend fun setDataLocal(words: List<Word>)
    suspend fun setDataFavorite(word: Word)
}