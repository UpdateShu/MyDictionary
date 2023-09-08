package com.geekbrains.viewmodel

import androidx.lifecycle.viewModelScope
import com.geekbrains.entities.AppState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val interactor: FavoriteInteractorInterface<AppState>,
    private val dispatcher: com.geekbrains.utils.AppDispatcher
)
    : com.geekbrains.viewmodel.BaseViewModel<AppState>()
{
    fun getFavoritesList() {
        liveData.postValue(com.geekbrains.entities.AppState.Loading(null))
        viewModelScope.launch {
            try {
                withContext(dispatcher.io) {
                    val response = interactor.getAllFavorite()
                    if (!response.isNullOrEmpty()) {
                        liveData.postValue(com.geekbrains.entities.AppState.Success(response))
                    } else {
                        liveData.postValue(com.geekbrains.entities.AppState.Error(com.geekbrains.utils.BODY_EMPTY))
                    }
                }
            } catch (e: Exception) {
                liveData.postValue(com.geekbrains.entities.AppState.Error(e.message.toString()))
            } finally {
                liveData.postValue(com.geekbrains.entities.AppState.Loading(1))
            }
        }
    }

    fun deleteIsFavorite(idWord: Int)
    {
        viewModelScope.launch {
            withContext(dispatcher.io) {
                interactor.deleteFavorite(idWord)
            }
        }
    }
}