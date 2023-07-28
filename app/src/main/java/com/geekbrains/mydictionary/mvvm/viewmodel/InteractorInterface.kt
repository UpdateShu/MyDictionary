package com.geekbrains.mydictionary.mvvm.viewmodel

import io.reactivex.rxjava3.core.Observable

interface InteractorInterface<T : Any> {
    fun getDataInteractor(word: String, fromRemoteSource: Boolean): Observable<T>
}