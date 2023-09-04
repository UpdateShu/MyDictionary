package com.geekbrains.mydictionary.mvvm.model.repo

import com.geekbrains.mydictionary.mvvm.model.repo.datasource.DataSourceInterface
import com.geekbrains.mydictionary.mvvm.model.entities.Word

class Repository(private val dataSource: DataSourceInterface<List<Word>>) :
    RepositoryInterface<List<Word>> {

    override suspend fun getDataRepository(word: String) = dataSource.getData(word)

    override suspend fun setDataLocal(words: List<Word>) {
        //TODO("Not yet implemented")
    }
}