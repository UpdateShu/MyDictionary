package com.geekbrains.mydictionary.mvp.model.data

import io.reactivex.rxjava3.core.Observable

interface DataSourceInterface<T : Any> {
    fun getData(word:String): Observable<T>
}