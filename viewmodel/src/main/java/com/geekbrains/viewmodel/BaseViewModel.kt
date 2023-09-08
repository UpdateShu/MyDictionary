package com.geekbrains.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.entities.AppState

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel<T : AppState> (
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable())
        : ViewModel()
{
    open fun getDataViewModel(word: String = "", isOnline: Boolean = true)
        : LiveData<T> = liveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}