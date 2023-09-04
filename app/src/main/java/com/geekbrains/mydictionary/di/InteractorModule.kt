package com.geekbrains.mydictionary.di

import com.geekbrains.mydictionary.mvvm.model.data.RepositoryInterface
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.viewmodel.MainInteractor

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: RepositoryInterface<List<Word>>,
        @Named(NAME_LOCAL) repositoryLocal: RepositoryInterface<List<Word>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}