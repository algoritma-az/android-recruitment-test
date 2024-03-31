package com.mismayilov.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mismayilov.data.database.daos.InvestModelDao
import com.mismayilov.domain.entities.InvestModel

@Database(entities = [InvestModel::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun socketModelDao(): InvestModelDao
}