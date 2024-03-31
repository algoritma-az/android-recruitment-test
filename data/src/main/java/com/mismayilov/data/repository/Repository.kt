package com.mismayilov.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mismayilov.core.utils.NetworkUtils
import com.mismayilov.domain.entities.InvestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val localRepository: LocalRepository,
    private val webSocketRepository: WebSocketRepository,
    private val context: Context
) {
    var isInitialDataFetched = false
    private val _liveData = MutableLiveData<List<InvestModel>>()
    private val _onConnect = MutableLiveData<Boolean>()
    val liveData: LiveData<List<InvestModel>> get() = _liveData
    val onConnect: LiveData<Boolean> get() = _onConnect


    suspend fun fetchData() {
        if (NetworkUtils.isNetworkAvailable(context)) {
            webSocketRepository.fetchData()
            webSocketRepository.liveData.observeForever {
                _liveData.postValue(it)
            }
            webSocketRepository.isSocketConnected.observeForever {
                _onConnect.postValue(it)
            }
        } else {
            localRepository.fetchData()
            localRepository.liveData.observeForever {
                _liveData.postValue(it)
            }
        }
    }

    fun disconnectWebSocket() {
        webSocketRepository.disconnectWebSocket()
    }

    suspend fun saveData() {
        withContext(Dispatchers.IO) {
            localRepository.saveData(_liveData.value!!)
        }
    }

}