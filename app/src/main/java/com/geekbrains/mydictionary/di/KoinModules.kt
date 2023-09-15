package com.geekbrains.mydictionary.di

import androidx.room.Room
import com.geekbrains.entities.AppState

import com.geekbrains.entities.Word
import com.geekbrains.entities.room.WordDB
import com.geekbrains.entities.room.WordDao

import com.geekbrains.utils.AppDispatcher

import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModel = module {
    single<CompositeDisposable> { CompositeDisposable() }
    single<com.geekbrains.viewmodel.InteractorInterface<AppState>> {
        com.geekbrains.viewmodel.MainInteractor(
            remoteRepository = get(named(REMOTE_REPOS)),
            localRepository = get(named(LOCAL_REPOS)),
            favoriteRepository = get(named(FAVORITE_REPOS))
        )
    }
    single<com.geekbrains.viewmodel.FavoriteInteractorInterface<AppState>>
    {
        com.geekbrains.viewmodel.FavoriteInteractor(favoriteRepository = get(named(FAVORITE_REPOS)))
    }

    factory<AppDispatcher> { AppDispatcher() }
    factory<CoroutineScope> { CoroutineScope(get<AppDispatcher>().default) }
    viewModel(named(com.geekbrains.utils.MAIN_VIEWMODEL))
    {
        com.geekbrains.viewmodel.MainViewModel(
            interactor = get(),
            scope = get(),
            dispatcher = get()
        )
    }
    viewModel(named(com.geekbrains.utils.FAVORITE_VIEWMODEL))
    {
        com.geekbrains.viewmodel.FavoriteViewModel(
            interactor = get(),
            dispatcher = get()
        )
    }

    single<com.geekbrains.repo.RepositoryInterface<List<Word>>>(named(REMOTE_REPOS)) {
        com.geekbrains.repo.Repository(dataSource = get(named(REMOTE_DS)))
    }
    single<com.geekbrains.repo.RepositoryInterface<List<Word>>>(named(LOCAL_REPOS)) {
        com.geekbrains.repo.Repository(dataSource = get(named(LOCAL_DS)))
    }
    single<com.geekbrains.repo.FavoriteRepositoryInterface<List<Word>>>(named(FAVORITE_REPOS)) {
        com.geekbrains.repo.FavoriteRepository(dataSource = get(named(FAVORITE_DS)))
    }

    single<com.geekbrains.repo.datasource.DataSourceInterface<List<Word>>>(named(REMOTE_DS)) {
        com.geekbrains.repo.datasource.RemoteDataSource(com.geekbrains.repo.datasource.retrofit.RetrofitDataSource())
    }
    single<com.geekbrains.repo.datasource.DataSourceInterface<List<Word>>>(named(LOCAL_DS)) {
        com.geekbrains.repo.datasource.LocalDataSource(
            com.geekbrains.repo.datasource.room.RoomDataSource(
                dao = get()
            )
        )
    }
    single<com.geekbrains.repo.datasource.DataSourceInterface<List<Word>>>(named(FAVORITE_DS)) {
        com.geekbrains.repo.datasource.FavoriteDataSource(
            com.geekbrains.repo.datasource.room.RoomFavoriteDataSource(
                dao = get()
            )
        )
    }

    single<WordDao> {
        get<WordDB>().getDao()
    }
    single<WordDB> {
        Room.databaseBuilder(androidContext(), WordDB::class.java, DATABASE_NAME).build()
    }
}

