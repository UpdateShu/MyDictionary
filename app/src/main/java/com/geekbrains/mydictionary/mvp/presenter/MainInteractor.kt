package com.geekbrains.mydictionary.mvp.presenter

import com.geekbrains.mydictionary.mvp.model.entities.AppState
import com.geekbrains.mydictionary.mvp.model.entities.Word
import com.geekbrains.mydictionary.mvp.model.data.RepositoryInterface
import io.reactivex.rxjava3.core.Observable

class MainInteractor(
    private val remoteRepository: RepositoryInterface<List<Word>>,
    private val localRepository: RepositoryInterface<List<Word>>)
    : InteractorInterface<AppState> {

    override fun getDataInteractor(
        word: String, fromRemoteSource: Boolean
    ): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getDataRepository(word)
                .map { AppState.Success(it) }
        } else {
            localRepository.getDataRepository(word)
                .map { AppState.Success(it) }
        }
    }
}