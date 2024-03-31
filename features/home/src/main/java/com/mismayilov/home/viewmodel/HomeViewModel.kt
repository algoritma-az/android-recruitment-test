package com.mismayilov.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mismayilov.data.repository.Repository
import com.mismayilov.domain.entities.InvestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val dataLiveData: LiveData<List<InvestModel>> = repository.liveData
    val onConnect: LiveData<Boolean> = repository.onConnect

    fun fetchData() {
        viewModelScope.launch {
            repository.fetchData()
        }
    }

    fun saveData() {
        if (dataLiveData.value.isNullOrEmpty()) return
        if (repository.isInitialDataFetched) return
        viewModelScope.launch {
            repository.saveData()
            repository.isInitialDataFetched = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveData()
        repository.disconnectWebSocket()
    }


}