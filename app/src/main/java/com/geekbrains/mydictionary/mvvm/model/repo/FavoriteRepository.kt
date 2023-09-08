package com.geekbrains.mydictionary.mvvm.model.repo

import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.DataSourceInterface

class FavoriteRepository(private val dataSource: DataSourceInterface<List<Word>>)
    : FavoriteRepositoryInterface<List<Word>>
{
    override suspend fun getAllFavorite(): List<Word> {
        return dataSource.getData()
    }

    override suspend fun deleteFavorite(idWord: Int) {
        dataSource.deleteData(idWord)
    }

    override suspend fun setFavoriteData(words: List<Word>) {
        dataSource.setDataLocal(words)
    }
}