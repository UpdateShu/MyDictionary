package com.geekbrains.mydictionary.mvp.model.data

import com.geekbrains.mydictionary.mvp.model.entities.Word
import io.reactivex.rxjava3.core.Observable

class Repository(private val dataSource: DataSourceInterface<List<Word>>) :
    RepositoryInterface<List<Word>> {

    override fun getDataRepository(word: String): Observable<List<Word>> =
        dataSource.getData(word)
}