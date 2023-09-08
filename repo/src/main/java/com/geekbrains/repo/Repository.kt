package com.geekbrains.repo

import com.geekbrains.repo.datasource.DataSourceInterface
import com.geekbrains.entities.Word

class Repository(private val dataSource: DataSourceInterface<List<Word>>)
    : RepositoryInterface<List<Word>>
{
    override suspend fun getDataRepository(word: String): List<Word> =
        dataSource.getDataBySearchWord(word)

    override suspend fun setDataLocal(words: List<Word>) {
        (dataSource as com.geekbrains.repo.datasource.LocalDataSource).setDataLocal(words)
    }

    override suspend fun setDataFavorite(word: Word) {
        (dataSource as com.geekbrains.repo.datasource.FavoriteDataSource).setDataLocal(listOf(word))
    }
    override suspend fun getDataFavorite() : List<Word> {
        return (dataSource as com.geekbrains.repo.datasource.FavoriteDataSource).getData()
    }
}