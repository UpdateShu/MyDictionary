package com.geekbrains.viewmodel

import androidx.lifecycle.LiveData
import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word
import com.geekbrains.utils.BODY_EMPTY

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

    override fun getDataViewModel(word: String, isOnline: Boolean): LiveData<AppState> {
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
        return super.getDataViewModel(word, isOnline)
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

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error.message.toString()))
    }
}