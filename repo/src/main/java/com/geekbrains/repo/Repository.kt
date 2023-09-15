package com.geekbrains.repo

import com.geekbrains.repo.datasource.DataSourceInterface
import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.FavoriteDataSource
import com.geekbrains.repo.datasource.LocalDataSource

class Repository(private val dataSource: DataSourceInterface<List<Word>>)
    : RepositoryInterface<List<Word>>
{
    override suspend fun getDataRepository(word: String)
        = dataSource.getDataBySearchWord(word)

    override suspend fun setDataLocal(words: List<Word>)
        = (dataSource as LocalDataSource).setDataLocal(words)

    override suspend fun setDataFavorite(word: Word)
        = (dataSource as FavoriteDataSource).setDataLocal(listOf(word))

    override suspend fun getDataFavorite()
        = (dataSource as FavoriteDataSource).getData()
}