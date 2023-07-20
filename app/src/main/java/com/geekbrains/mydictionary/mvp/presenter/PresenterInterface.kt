package com.geekbrains.mydictionary.mvp.presenter

import com.geekbrains.mydictionary.mvp.view.ViewInterface

interface PresenterInterface {
    fun onAttach(view: ViewInterface)
    fun onDetach(view: ViewInterface)
    fun getDataPresenter(word: String, isOnline: Boolean)
}