package com.geekbrains.mydictionary.mvvm.model.repo.datasource

interface DataSourceInterface<T : Any> {
    suspend fun getData(word: String): T
}