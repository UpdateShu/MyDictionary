package com.geekbrains.mydictionary.mvp.model.data

import io.reactivex.rxjava3.core.Observable

interface RepositoryInterface<T : Any> {
    fun getDataRepository(word: String): Observable<T>
}