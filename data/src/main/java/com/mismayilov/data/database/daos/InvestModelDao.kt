package com.mismayilov.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mismayilov.domain.entities.InvestModel

@Dao
interface InvestModelDao {
    @Insert
    suspend fun insert(socketModel: List<InvestModel>)

    @Query("SELECT * FROM socket_model_table")
    suspend fun getAll(): List<InvestModel>

    @Query("DELETE FROM socket_model_table")
    suspend fun deleteAll()
}