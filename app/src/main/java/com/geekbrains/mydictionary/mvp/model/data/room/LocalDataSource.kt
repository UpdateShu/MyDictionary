package com.geekbrains.mydictionary.mvp.model.data.room

import com.geekbrains.mydictionary.mvp.model.data.DataSourceInterface
import com.geekbrains.mydictionary.mvp.model.entities.Word
import io.reactivex.rxjava3.core.Observable

class LocalDataSource(private val provider: RoomDataSource)
    : DataSourceInterface<List<Word>> {

    override fun getData(word: String): Observable<List<Word>> =
        provider.getData(word)
}