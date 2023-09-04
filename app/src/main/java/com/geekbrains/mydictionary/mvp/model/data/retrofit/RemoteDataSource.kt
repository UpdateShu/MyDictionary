package com.geekbrains.mydictionary.mvp.model.data.retrofit

import com.geekbrains.mydictionary.mvp.model.data.DataSourceInterface
import com.geekbrains.mydictionary.mvp.model.entities.Word
import io.reactivex.rxjava3.core.Observable

class RemoteDataSource(private val provider: RetrofitDataSource)
    : DataSourceInterface<List<Word>> {

    override fun getData(word: String): Observable<List<Word>> =
        provider.getData(word)
}