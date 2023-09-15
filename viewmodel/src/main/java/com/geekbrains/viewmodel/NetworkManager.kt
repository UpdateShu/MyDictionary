package com.geekbrains.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NetworkManager(
    private val scope: CoroutineScope, context: Context
) {
    private var job: Job? = null

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val request: NetworkRequest = NetworkRequest.Builder().build()

    private val mutableConnection = MutableStateFlow(true)
    val connection: StateFlow<Boolean> = mutableConnection

    private var isConnection = false

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            isConnection = false
        }

        override fun onAvailable(network: Network) {
            isConnection = true
        }
    }

    fun init() {
        if (job == null) {
            job = scope.launch {
                while (isActive) {
                    mutableConnection.value = isConnection
                    delay(100)
                }
            }
        }
        connectivityManager.registerNetworkCallback(request, callback)
    }

    fun clear() {
        connectivityManager.unregisterNetworkCallback(callback)
        job?.cancel()
        job = null
    }
}