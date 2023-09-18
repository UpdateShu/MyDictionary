package com.geekbrains.mydictionary.di

import androidx.room.Room
import com.geekbrains.entities.AppState

import com.geekbrains.entities.Word
import com.geekbrains.entities.room.WordDB
import com.geekbrains.entities.room.WordDao
import com.geekbrains.mydictionary.view.MainActivity
import com.geekbrains.repo.FavoriteRepository
import com.geekbrains.repo.FavoriteRepositoryInterface
import com.geekbrains.repo.Repository
import com.geekbrains.repo.RepositoryInterface

import com.geekbrains.repo.datasource.DataSourceInterface
import com.geekbrains.repo.datasource.FavoriteDataSource
import com.geekbrains.repo.datasource.LocalDataSource
import com.geekbrains.repo.datasource.RemoteDataSource
import com.geekbrains.repo.datasource.retrofit.RetrofitDataSource
import com.geekbrains.repo.datasource.room.RoomDataSource
import com.geekbrains.repo.datasource.room.RoomFavoriteDataSource

import com.geekbrains.utils.AppDispatcher
import com.geekbrains.utils.FAVORITE_VIEWMODEL

import com.geekbrains.viewmodel.FavoriteInteractor
import com.geekbrains.viewmodel.FavoriteInteractorInterface
import com.geekbrains.viewmodel.FavoriteViewModel
import com.geekbrains.viewmodel.InteractorInterface
import com.geekbrains.viewmodel.MainInteractor
import com.geekbrains.viewmodel.MainViewModel

import kotlinx.coroutines.CoroutineScope

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped<InteractorInterface<AppState>> {
            MainInteractor(
                remoteRepository = get(named(REMOTE_REPOS)),
                localRepository = get(named(LOCAL_REPOS)),
                favoriteRepository = get(named(FAVORITE_REPOS))
            )
        }
        viewModel {
            MainViewModel(
                interactor = get()
            )
        }
    }
}

val appModel = module {

    single<FavoriteInteractorInterface<AppState>>
    {
        FavoriteInteractor(favoriteRepository = get(named(FAVORITE_REPOS)))
    }

    factory<AppDispatcher> { AppDispatcher() }
    factory<CoroutineScope> { CoroutineScope(get<AppDispatcher>().default) }

    viewModel(named(FAVORITE_VIEWMODEL))
    {
        FavoriteViewModel(
            interactor = get()
        )
    }

    single<RepositoryInterface<List<Word>>>(named(REMOTE_REPOS)) {
        Repository(dataSource = get(named(REMOTE_DS)))
    }
    single<RepositoryInterface<List<Word>>>(named(LOCAL_REPOS)) {
        Repository(dataSource = get(named(LOCAL_DS)))
    }
    single<FavoriteRepositoryInterface<List<Word>>>(named(FAVORITE_REPOS)) {
        FavoriteRepository(dataSource = get(named(FAVORITE_DS)))
    }

    single<DataSourceInterface<List<Word>>>(named(REMOTE_DS)) {
        RemoteDataSource(RetrofitDataSource())
    }
    single<DataSourceInterface<List<Word>>>(named(LOCAL_DS)) {
        LocalDataSource(
            RoomDataSource(
                dao = get()
            )
        )
    }
    single<DataSourceInterface<List<Word>>>(named(FAVORITE_DS)) {
        FavoriteDataSource(
            RoomFavoriteDataSource(
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

