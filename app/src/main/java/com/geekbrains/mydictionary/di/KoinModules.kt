package com.geekbrains.mydictionary.di

import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.mvvm.model.repo.Repository
import com.geekbrains.mydictionary.mvvm.model.repo.RepositoryInterface
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.RoomDataSource
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.RetrofitDataSource
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.viewmodel.InteractorInterface

import com.geekbrains.mydictionary.mvvm.viewmodel.MainInteractor
import com.geekbrains.mydictionary.mvvm.viewmodel.MainViewModel
import com.geekbrains.mydictionary.utils.AppDispatcher
import com.geekbrains.mydictionary.utils.MAIN_VIEWMODEL
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModel = module {
    single<CompositeDisposable> { CompositeDisposable() }
    single<InteractorInterface<AppState>> {
        MainInteractor(
            remoteRepository = get(named(NAME_REMOTE)),
            localRepository = get(named(NAME_LOCAL))
        )
    }

    single<RepositoryInterface<List<Word>>>(named(NAME_REMOTE)) { Repository(RetrofitDataSource()) }
    single<RepositoryInterface<List<Word>>>(named(NAME_LOCAL)) { Repository(RoomDataSource()) }

    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }

    factory<AppDispatcher> { AppDispatcher() }
    factory<CoroutineScope> { CoroutineScope(get<AppDispatcher>().default) }
    viewModel(named(MAIN_VIEWMODEL)) {
        MainViewModel(
            interactor = get(),
            scope = get(),
            dispatcher = get()
        )
    }
}