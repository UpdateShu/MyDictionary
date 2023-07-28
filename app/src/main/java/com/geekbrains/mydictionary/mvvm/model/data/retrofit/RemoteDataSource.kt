package com.geekbrains.mydictionary.mvvm.model.data.retrofit

import com.geekbrains.mydictionary.mvvm.model.data.DataSourceInterface
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import io.reactivex.rxjava3.core.Observable

class RemoteDataSource(private val provider: RetrofitDataSource)
    : DataSourceInterface<List<Word>> {

    override fun getData(word: String): Observable<List<Word>> =
        provider.getData(word)
}