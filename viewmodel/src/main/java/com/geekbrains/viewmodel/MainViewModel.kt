package com.geekbrains.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData

import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word
import com.geekbrains.utils.BODY_EMPTY
import com.geekbrains.utils.NetworkManager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel constructor(
    private val interactor: InteractorInterface<AppState>)
    : BaseViewModel<AppState>()
{
    private var job: Job? = null
    private var jobSetRoom: Job? = null

    private var networkManager: NetworkManager? = null
    private var isOnline : Boolean = true

    override fun getDataViewModel(word: String): LiveData<AppState> {
        liveData.postValue(AppState.Loading(null))
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = viewModelCoroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    interactor.getDataInteractor(word, isOnline).let { result ->
                        withContext(Dispatchers.Main) {
                            liveData.postValue(AppState.Loading(1))
                        }
                        if (result.isNotEmpty()) {
                            withContext(Dispatchers.IO) {
                                liveData.postValue(AppState.Success(result))
                                interactor.setDataLocal(result)
                            }
                        } else {
                            liveData.postValue(AppState.Error(BODY_EMPTY))
                        }
                    }
                }
            } catch (e: Exception) {
                liveData.postValue(AppState.Error(e.message.toString()))
                liveData.postValue(AppState.Loading(e.message?.length))
            }
        }
        return super.getDataViewModel(word)
    }

    fun initNetworkValidation(context: Context) : LiveData<AppState> {
        networkManager = NetworkManager(viewModelCoroutineScope, context)
        networkManager?.let {
            it.init()
            viewModelCoroutineScope.launch {
                it.connection.collect {
                    if (it != isOnline) {
                        isOnline = it
                        liveData.postValue(AppState.OnlineChanged(isOnline))
                    }
                }
            }
        }
        return liveData
    }

    fun setFavorite(word: Word)
    {
        jobSetRoom = viewModelCoroutineScope
            .launch {
                withContext(Dispatchers.IO) {
                    interactor.setDataFavorite(word)
                }
            }
    }

    override fun onCleared() {
        networkManager?.clear()
        super.onCleared()
    }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error.message.toString()))
    }
}