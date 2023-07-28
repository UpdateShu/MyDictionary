package com.geekbrains.mydictionary.mvvm.model.data

import io.reactivex.rxjava3.core.Observable

interface RepositoryInterface<T : Any> {
    fun getDataRepository(word: String): Observable<T>
}