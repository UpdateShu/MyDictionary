package com.geekbrains.repo.datasource

import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.room.RoomFavoriteDataSource

class FavoriteDataSource(private val provider: RoomFavoriteDataSource)
    : DataSourceInterface<List<Word>>
{
    override suspend fun getDataBySearchWord(word: String): List<Word> {
        return provider.getDataBySearchWord(word)
    }

    override suspend fun getData(): List<Word> {
        return provider.getData()
    }

    override suspend fun setDataLocal(words: List<Word>) {
        provider.setDataLocal(words)
    }

    override suspend fun deleteData(idWord: Int) {
        provider.deleteData(idWord)
    }
}