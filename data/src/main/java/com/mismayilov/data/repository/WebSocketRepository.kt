package com.mismayilov.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abbtech.firstabbtechapp.domain.repositories.SocketRepository
import com.google.gson.Gson
import com.mismayilov.core.utils.Util
import com.mismayilov.domain.entities.InvestModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketRepository @Inject constructor(): SocketRepository{
    private lateinit var socket: Socket
    private val _liveData = MutableLiveData<List<InvestModel>>()
    private val _isSocketConnected = MutableLiveData<Boolean>()
    val liveData: LiveData<List<InvestModel>> get() = _liveData
    val isSocketConnected: LiveData<Boolean> get() = _isSocketConnected
    private var isConnectionEstablished = false

    private fun setupSocketListeners() {
        socket.on(Socket.EVENT_CONNECT) {
            postConnectionStatus(true)
        }.on(Socket.EVENT_CONNECT_ERROR) {
            postConnectionStatus(false)
        }.on(Socket.EVENT_DISCONNECT) {
            postConnectionStatus(false)
        }.on("message") { args ->
            postConnectionStatus(true)
            val data = args[0] as JSONObject
            processData(data)
        }
    }

    private fun postConnectionStatus(isConnected: Boolean) {
        if (isConnectionEstablished != isConnected) {
            isConnectionEstablished = isConnected
            _isSocketConnected.postValue(isConnected)
        }
    }

    private fun processData(data: JSONObject) {
        if (_isSocketConnected.value == false) return
        val result = data.getJSONArray("result")
        val dataModel = Gson().fromJson(result.toString(), Array<InvestModel>::class.java).toList()
        _liveData.postValue(dataModel)
    }
    fun disconnectWebSocket() {
        if (socket.connected()) {
            socket.disconnect()
        }
    }

    override suspend fun fetchData() {
        initSocket()
        setupSocketListeners()
    }

    private fun initSocket() {
        try {
            socket = IO.socket(Util.BASE_URL, IO.Options().apply {
                reconnection = true
                reconnectionDelay = 3000
                reconnectionDelayMax = 10000
            })
            socket.connect()
        } catch (e: Exception) {
            postConnectionStatus(false)
        }
    }


}