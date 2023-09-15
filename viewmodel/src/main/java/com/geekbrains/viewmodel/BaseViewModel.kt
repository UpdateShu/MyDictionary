package com.geekbrains.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.geekbrains.entities.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : AppState> (
    protected val liveData: MutableLiveData<T> = MutableLiveData())
    : ViewModel()
{
    private var networkManager: NetworkManager? = null
    var networkIsOnline : Boolean = false

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    fun initNetworkValidation(context: Context) {
        networkManager = NetworkManager(viewModelCoroutineScope, context)
        networkManager?.let {
            it.init()
            viewModelCoroutineScope.launch {
                it.connection.collect {
                    networkIsOnline = it
                    Log.v("MyDictionary", networkIsOnline.toString())
                }
            }
        }
    }

    open fun getDataViewModel(word: String = "", isOnline: Boolean = true)
            : LiveData<T> = liveData

    override fun onCleared() {
        networkManager?.let {
            it.clear()
        }
        cancelJob()
        super.onCleared()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)
}