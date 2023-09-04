package com.geekbrains.mydictionary.mvvm.view

import com.geekbrains.mydictionary.mvvm.model.entities.AppState

interface ViewInterface {
    fun rangeData(state: AppState)
}