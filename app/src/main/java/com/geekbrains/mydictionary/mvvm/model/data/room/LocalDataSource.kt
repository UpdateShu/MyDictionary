package com.geekbrains.mydictionary.mvvm.model.data.room

import com.geekbrains.mydictionary.mvvm.model.data.DataSourceInterface
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import io.reactivex.rxjava3.core.Observable

class LocalDataSource(private val provider: RoomDataSource)
    : DataSourceInterface<List<Word>> {

    override fun getData(word: String): Observable<List<Word>> =
        provider.getData(word)
}