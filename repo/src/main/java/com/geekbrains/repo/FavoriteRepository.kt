package com.geekbrains.repo

import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.DataSourceInterface

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