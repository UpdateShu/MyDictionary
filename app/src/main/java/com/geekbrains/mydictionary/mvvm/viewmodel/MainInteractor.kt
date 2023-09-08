package com.geekbrains.mydictionary.mvvm.viewmodel

import com.geekbrains.entities.Word
import com.geekbrains.repo.FavoriteRepositoryInterface
import com.geekbrains.repo.RepositoryInterface

class MainInteractor (
    private val remoteRepository: com.geekbrains.repo.RepositoryInterface<List<Word>>,
    private val localRepository: com.geekbrains.repo.RepositoryInterface<List<Word>>,
    private val favoriteRepository: com.geekbrains.repo.FavoriteRepositoryInterface<List<Word>>
)
    : InteractorInterface<com.geekbrains.entities.AppState> {

    override suspend fun getDataInteractor(
        word: String, fromRemoteSource: Boolean)
        : List<com.geekbrains.entities.Word>
    {
        return if (fromRemoteSource) {
            remoteRepository.getDataRepository(word)
        } else {
            localRepository.getDataRepository(word)
        }
    }

    override suspend fun setDataLocal(words: List<com.geekbrains.entities.Word>) {
        localRepository.setDataLocal(words)
    }

    override suspend fun setDataFavorite(word: com.geekbrains.entities.Word) {
        favoriteRepository.setFavoriteData(listOf(word))
    }
}