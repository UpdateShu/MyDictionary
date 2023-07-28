package com.geekbrains.mydictionary.mvvm.model.data

import io.reactivex.rxjava3.core.Observable

interface DataSourceInterface<T : Any> {
    fun getData(word:String): Observable<T>
}