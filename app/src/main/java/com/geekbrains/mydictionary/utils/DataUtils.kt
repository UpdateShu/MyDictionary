package com.geekbrains.mydictionary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View

fun View.isOnline(context: Context): Boolean {
    val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

const val RELOAD_ONLINE = "Reload Online"
const val RELOAD_LOCAL = "Reload Local"
const val BODY_EMPTY = "Ничего не найдено!"
const val MAIN_VIEWMODEL = "MAIN_VIEWMODEL!"