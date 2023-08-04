package com.geekbrains.mydictionary.mvvm.model.repo.datasource

import com.geekbrains.mydictionary.mvvm.model.entities.Word

class RoomDataSource: DataSourceInterface<List<Word>> {
    override suspend fun getData(word: String): List<Word> {
        TODO("Not yet implemented")
    }
}