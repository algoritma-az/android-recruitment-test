package com.mismayilov.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abbtech.firstabbtechapp.domain.repositories.SocketRepository
import com.mismayilov.data.database.daos.InvestModelDao
import com.mismayilov.domain.entities.InvestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val dao: InvestModelDao
) : SocketRepository {
    val _liveData = MutableLiveData<List<InvestModel>>()
    val liveData:LiveData<List<InvestModel>> get() = _liveData

    override suspend fun fetchData() {
        withContext(Dispatchers.IO) {
            _liveData.postValue(dao.getAll())
        }
    }
    suspend fun saveData(data: List<InvestModel>) {
        if (data.isNullOrEmpty()) return
        withContext(Dispatchers.IO) {
            dao.deleteAll()
            dao.insert(data)
        }
    }
}
