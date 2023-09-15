package com.geekbrains.repo.datasource

import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.retrofit.RetrofitDataSource

class RemoteDataSource(private val provider: RetrofitDataSource)
    : DataSourceInterface<List<Word>>
{
    override suspend fun getDataBySearchWord(word: String) = provider.getDataBySearchWord(word)

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