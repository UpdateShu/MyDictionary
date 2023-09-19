package com.geekbrains.viewmodel

import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word

import com.geekbrains.repo.FavoriteRepositoryInterface
import com.geekbrains.repo.RepositoryInterface

class MainInteractor (
    private val remoteRepository: RepositoryInterface<List<Word>>,
    private val localRepository: RepositoryInterface<List<Word>>,
    private val favoriteRepository: FavoriteRepositoryInterface<List<Word>>
)
    : InteractorInterface<AppState> {

    override suspend fun getDataInteractor(word: String, fromRemoteSource: Boolean)
        : List<Word>
    {
        return if (fromRemoteSource) {
            remoteRepository.getDataRepository(word)
        } else {
            localRepository.getDataRepository(word)
        }
    }

    override suspend fun setDataLocal(words: List<Word>) {
        localRepository.setDataLocal(words)
    }

    override suspend fun setDataFavorite(word: Word) {
        favoriteRepository.setFavoriteData(listOf(word))
    }
}