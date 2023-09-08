package com.geekbrains.entities

sealed class AppState {
    data class Success(val data: List<Word>) : AppState()
    data class Error(val error: String) : AppState()
    data class Loading(val progress: Int?) : AppState()
}