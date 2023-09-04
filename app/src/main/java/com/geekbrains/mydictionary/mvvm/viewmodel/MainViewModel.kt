package com.geekbrains.mydictionary.mvvm.viewmodel

import androidx.lifecycle.LiveData
import com.geekbrains.mydictionary.mvvm.model.entities.AppState

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor)
    : BaseViewModel<AppState>()
{
    override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getDataInteractor(word, isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveData.postValue(AppState.Loading(null))
                }
                .subscribeBy(
                    onNext = { state ->
                        liveData.postValue(state)
                    },
                    onError = { error ->
                        liveData.postValue(AppState.Error(error))
                    },
                    onComplete = {
                        liveData.postValue(AppState.Loading(1))
                    }
                )
        )
        return super.getDataViewModel(word, isOnline)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}