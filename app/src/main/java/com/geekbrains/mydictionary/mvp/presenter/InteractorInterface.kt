package com.geekbrains.mydictionary.mvp.presenter

import io.reactivex.rxjava3.core.Observable

interface InteractorInterface<T : Any> {
    fun getDataInteractor(word: String, fromRemoteSource: Boolean): Observable<T>
}