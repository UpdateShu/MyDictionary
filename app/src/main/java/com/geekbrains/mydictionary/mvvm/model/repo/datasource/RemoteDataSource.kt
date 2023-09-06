package com.geekbrains.mydictionary.mvvm.model.repo.datasource

import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.retrofit.RetrofitDataSource

class RemoteDataSource(private val provider: RetrofitDataSource)
    : DataSourceInterface<List<Word>>
{
    override suspend fun getDataBySearchWord(word: String): List<Word> =
        provider.getDataBySearchWord(word)

    override suspend fun setDataLocal(words: List<Word>) {
        TODO("Not yet implemented")
    }

    override suspend fun getData(): List<Word> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(idWord: Int) {
        TODO("Not yet implemented")
    }
}