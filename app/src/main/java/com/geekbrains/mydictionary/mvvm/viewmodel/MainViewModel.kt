package com.geekbrains.mydictionary.mvvm.viewmodel

import androidx.lifecycle.LiveData
import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.utils.AppDispatcher
import com.geekbrains.mydictionary.utils.BODY_EMPTY

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel constructor(
    private val interactor: MainInteractor,
    private val scope: CoroutineScope,
    private val dispatcher: AppDispatcher) : BaseViewModel<AppState>()
{
    private var job: Job? = null

    override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<AppState> {
        liveData.postValue(AppState.Loading(null))
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = scope.launch() {
            try {
                withContext(dispatcher.io) {
                    interactor.getDataInteractor(word, isOnline).let { result ->
                        withContext(dispatcher.main) {
                            liveData.postValue(AppState.Loading(1))
                        }
                        if (!result.isNullOrEmpty()) {
                            withContext(dispatcher.io) {
                                liveData.postValue(AppState.Success(result))
                                interactor.setDataLocal(result)
                            }
                        } else if (result.isEmpty()) {
                            liveData.postValue(AppState.Error(BODY_EMPTY))
                        }
                    }
                }
            } catch (e: Exception) {
                liveData.postValue(AppState.Error(e.message.toString()))
                liveData.postValue(AppState.Loading(e.message?.length))
            }
        }
        return super.getDataViewModel(word, isOnline)
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}