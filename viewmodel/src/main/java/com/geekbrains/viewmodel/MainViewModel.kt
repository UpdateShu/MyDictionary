package com.geekbrains.viewmodel

import androidx.lifecycle.LiveData
import com.geekbrains.entities.AppState

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel constructor(
    private val interactor: InteractorInterface<AppState>,
    private val scope: CoroutineScope,
    private val dispatcher: com.geekbrains.utils.AppDispatcher
) : com.geekbrains.viewmodel.BaseViewModel<AppState>()
{
    private var job: Job? = null
    private var jobSetRoom: Job? = null

    override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<com.geekbrains.entities.AppState> {
        liveData.postValue(com.geekbrains.entities.AppState.Loading(null))
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = scope.launch() {
            try {
                withContext(dispatcher.io) {
                    interactor.getDataInteractor(word, isOnline).let { result ->
                        withContext(dispatcher.main) {
                            liveData.postValue(com.geekbrains.entities.AppState.Loading(1))
                        }
                        if (!result.isNullOrEmpty()) {
                            withContext(dispatcher.io) {
                                liveData.postValue(com.geekbrains.entities.AppState.Success(result))
                                interactor.setDataLocal(result)
                            }
                        } else if (result.isEmpty()) {
                            liveData.postValue(com.geekbrains.entities.AppState.Error(com.geekbrains.utils.BODY_EMPTY))
                        }
                    }
                }
            } catch (e: Exception) {
                liveData.postValue(com.geekbrains.entities.AppState.Error(e.message.toString()))
                liveData.postValue(com.geekbrains.entities.AppState.Loading(e.message?.length))
            }
        }
        return super.getDataViewModel(word, isOnline)
    }

    fun setFavorite(word: com.geekbrains.entities.Word) {
        jobSetRoom = scope
            .launch {
                withContext(dispatcher.io) {
                    interactor.setDataFavorite(word)
                }
            }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}