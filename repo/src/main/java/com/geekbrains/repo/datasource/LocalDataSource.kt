package com.geekbrains.repo.datasource

import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.room.RoomDataSource

class LocalDataSource(private val provider: RoomDataSource)
    : DataSourceInterface<List<Word>>
{
    override suspend fun getDataBySearchWord(word: String): List<Word> =
        provider.getDataBySearchWord(word)

    override suspend fun setDataLocal(words: List<Word>) {
        provider.setDataLocal(words)
    }

    override suspend fun getData(): List<Word> {
        return provider.getData()
    }

    override suspend fun deleteData(idWord: Int) {
        TODO("Not yet implemented")
    }
}