package com.mismayilov.core.listener

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mismayilov.core.utils.NetworkUtils

class ConnectivityViewModel : ViewModel() {
    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    fun checkInternetConnection(context: Context) {
        val isConnected = NetworkUtils.isNetworkAvailable(context)
        _isConnected.postValue(isConnected)
    }
}