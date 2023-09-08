package com.geekbrains.mydictionary.mvvm.viewmodel

import androidx.lifecycle.viewModelScope
import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.utils.AppDispatcher
import com.geekbrains.mydictionary.utils.BODY_EMPTY
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val interactor: FavoriteInteractorInterface<AppState>,
    private val dispatcher: AppDispatcher)
    : BaseViewModel<AppState>()
{
    fun getFavoritesList() {
        liveData.postValue(AppState.Loading(null))
        viewModelScope.launch {
            try {
                withContext(dispatcher.io) {
                    val response = interactor.getAllFavorite()
                    if (!response.isNullOrEmpty()) {
                        liveData.postValue(AppState.Success(response))
                    } else {
                        liveData.postValue(AppState.Error(BODY_EMPTY))
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
        viewModelScope.launch {
            withContext(dispatcher.io) {
                interactor.deleteFavorite(idWord)
            }
        }
    }
}