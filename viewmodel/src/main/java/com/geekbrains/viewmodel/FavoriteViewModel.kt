package com.geekbrains.viewmodel

import com.geekbrains.entities.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val interactor: FavoriteInteractorInterface<AppState>)
    : BaseViewModel<AppState>()
{
    fun getFavoritesList() {
        liveData.postValue(AppState.Loading(null))
        viewModelCoroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val response = interactor.getAllFavorite()
                    if (!response.isNullOrEmpty()) {
                        liveData.postValue(AppState.Success(response))
                    } else {
                        liveData.postValue(AppState.Error(com.geekbrains.utils.BODY_EMPTY))
                    }
                }
            } catch (e: Exception) {
                liveData.postValue(AppState.Error(e.message.toString()))
            } finally {
                liveData.postValue(AppState.Loading(1))
            }
        }
    }

    fun deleteIsFavorite(idWord: Int)
    {
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                interactor.deleteFavorite(idWord)
            }
        }
    }

    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error.message.toString()))
    }
}