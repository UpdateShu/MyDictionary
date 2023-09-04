package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.mydictionary.di.NAME_LOCAL
import com.geekbrains.mydictionary.di.NAME_REMOTE
import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.data.RepositoryInterface

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: RepositoryInterface<List<Word>>,
    @Named(NAME_LOCAL) val localRepository: RepositoryInterface<List<Word>>
)
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