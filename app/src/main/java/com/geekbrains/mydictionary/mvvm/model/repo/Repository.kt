package com.geekbrains.mydictionary.mvvm.model.repo

import com.geekbrains.mydictionary.mvvm.model.repo.datasource.DataSourceInterface
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.FavoriteDataSource
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.LocalDataSource

class Repository(private val dataSource: DataSourceInterface<List<Word>>)
    : RepositoryInterface<List<Word>>
{
    override suspend fun getDataRepository(word: String): List<Word> =
        dataSource.getDataBySearchWord(word)

    override suspend fun setDataLocal(words: List<Word>) {
        (dataSource as LocalDataSource).setDataLocal(words)
    }

    override suspend fun setDataFavorite(word: Word) {
        (dataSource as FavoriteDataSource).setDataLocal(listOf(word))
    }
    override suspend fun getDataFavorite() : List<Word> {
        return (dataSource as FavoriteDataSource).getData()
    }
}