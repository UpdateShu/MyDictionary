package com.geekbrains.mydictionary.mvp.view

import com.geekbrains.mydictionary.mvp.model.entities.AppState

interface ViewInterface {
    fun rangeData(state: AppState)
}