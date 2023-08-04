package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.mydictionary.mvvm.model.entities.Word

interface InteractorInterface<T : Any> {
    suspend fun getDataInteractor(word: String, fromRemoteSource: Boolean): List<Word>
    suspend fun setDataLocal(words: List<Word>)
}