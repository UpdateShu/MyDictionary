package com.geekbrains.mydictionary

import android.app.Application
import com.geekbrains.mydictionary.di.appModel
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModel)
        }
    }
}