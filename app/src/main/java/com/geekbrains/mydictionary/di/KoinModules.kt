package com.geekbrains.mydictionary.di

import androidx.room.Room
import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.mvvm.model.repo.Repository
import com.geekbrains.mydictionary.mvvm.model.repo.RepositoryInterface
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.room.RoomDataSource

import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.entities.room.WordDB
import com.geekbrains.mydictionary.mvvm.model.entities.room.WordDao

import com.geekbrains.mydictionary.mvvm.model.repo.FavoriteRepository
import com.geekbrains.mydictionary.mvvm.model.repo.FavoriteRepositoryInterface
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.DataSourceInterface
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.FavoriteDataSource
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.LocalDataSource
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.RemoteDataSource
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.retrofit.RetrofitDataSource
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.room.RoomFavoriteDataSource

import com.geekbrains.mydictionary.mvvm.viewmodel.FavoriteInteractor
import com.geekbrains.mydictionary.mvvm.viewmodel.FavoriteInteractorInterface
import com.geekbrains.mydictionary.mvvm.viewmodel.FavoriteViewModel
import com.geekbrains.mydictionary.mvvm.viewmodel.InteractorInterface
import com.geekbrains.mydictionary.mvvm.viewmodel.MainInteractor
import com.geekbrains.mydictionary.mvvm.viewmodel.MainViewModel

import com.geekbrains.mydictionary.utils.AppDispatcher
import com.geekbrains.mydictionary.utils.FAVORITE_VIEWMODEL
import com.geekbrains.mydictionary.utils.MAIN_VIEWMODEL

import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModel = module {
    single<CompositeDisposable> { CompositeDisposable() }
    single<InteractorInterface<AppState>> {
        MainInteractor(
            remoteRepository = get(named(REMOTE_REPOS)),
            localRepository = get(named(LOCAL_REPOS)),
            favoriteRepository = get(named(FAVORITE_REPOS))
        )
    }
    single<FavoriteInteractorInterface<AppState>>
    {
        FavoriteInteractor(favoriteRepository = get(named(FAVORITE_REPOS)))
    }

    factory<AppDispatcher> { AppDispatcher() }
    factory<CoroutineScope> { CoroutineScope(get<AppDispatcher>().default) }
    viewModel(named(MAIN_VIEWMODEL))
    {
        MainViewModel(
            interactor = get(),
            scope = get(),
            dispatcher = get()
        )
    }
    viewModel(named(FAVORITE_VIEWMODEL))
    {
        FavoriteViewModel(
            interactor = get(),
            dispatcher = get()
        )
    }

    single<RepositoryInterface<List<Word>>>(named(REMOTE_REPOS)) {
        Repository(dataSource = get(named(REMOTE_DS))) }
    single<RepositoryInterface<List<Word>>>(named(LOCAL_REPOS)) {
        Repository(dataSource = get(named(LOCAL_DS))) }
    single<FavoriteRepositoryInterface<List<Word>>>(named(FAVORITE_REPOS)) {
        FavoriteRepository(dataSource = get(named(FAVORITE_DS)))
    }

    single<DataSourceInterface<List<Word>>>(named(REMOTE_DS)) {
        RemoteDataSource(RetrofitDataSource())
    }
    single<DataSourceInterface<List<Word>>>(named(LOCAL_DS)) {
        LocalDataSource(RoomDataSource(dao = get()))
    }
    single<DataSourceInterface<List<Word>>>(named(FAVORITE_DS)) {
        FavoriteDataSource(RoomFavoriteDataSource(dao = get()))
    }
    single<WordDao> {
        Room.databaseBuilder(androidContext(),
            WordDB::class.java, DATABASE_NAME).build().getDao()
    }
}