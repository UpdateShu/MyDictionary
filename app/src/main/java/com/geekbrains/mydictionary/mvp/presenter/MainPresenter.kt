package com.geekbrains.mydictionary.mvp.presenter

import com.geekbrains.mydictionary.mvp.model.data.room.LocalDataSource
import com.geekbrains.mydictionary.mvp.model.data.retrofit.RemoteDataSource
import com.geekbrains.mydictionary.mvp.model.data.Repository
import com.geekbrains.mydictionary.mvp.model.data.retrofit.RetrofitDataSource

import com.geekbrains.mydictionary.mvp.model.data.room.RoomDataSource
import com.geekbrains.mydictionary.mvp.model.entities.AppState
import com.geekbrains.mydictionary.mvp.view.ViewInterface

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter(
    private val interactor: MainInteractor = MainInteractor(
        Repository(RemoteDataSource(RetrofitDataSource())),
        Repository(LocalDataSource(RoomDataSource()))
    ),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable())
        : PresenterInterface {

    private var currentView: ViewInterface? = null

    override fun onAttach(view: ViewInterface) {
        if (currentView != view) {
            currentView = view
        }
    }

    override fun onDetach(view: ViewInterface) {
        if (currentView == view) {
            currentView = null
        }
    }

    override fun getDataPresenter(word: String, isOnline: Boolean) {

        compositeDisposable.add(
            interactor.getDataInteractor(word, isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    currentView?.rangeData(AppState.Loading(null))
                }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {

        return object : DisposableObserver<AppState>() {
            override fun onNext(t: AppState) {
                currentView?.rangeData(t)
                currentView?.rangeData(AppState.Loading(1))
            }

            override fun onError(e: Throwable) {
                currentView?.rangeData(AppState.Error(e))
                currentView?.rangeData(AppState.Loading(1))
            }

            override fun onComplete() {
                currentView?.rangeData(AppState.Loading(1))
            }
        }
    }

}