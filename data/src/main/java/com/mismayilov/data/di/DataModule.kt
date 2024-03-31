package com.mismayilov.data.di

import android.content.Context
import androidx.room.Room
import com.mismayilov.core.utils.Util
import com.mismayilov.data.database.daos.InvestModelDao
import com.mismayilov.data.database.db.AppDatabase
import com.mismayilov.data.repository.LocalRepository
import com.mismayilov.data.repository.Repository
import com.mismayilov.data.repository.WebSocketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, Util.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideSocketModelDao(appDatabase: AppDatabase): InvestModelDao {
        return appDatabase.socketModelDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(socketModelDao: InvestModelDao): LocalRepository {
        return LocalRepository(socketModelDao)
    }

    @Provides
    @Singleton
    fun providecontext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): WebSocketRepository {
        return WebSocketRepository()
    }

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalRepository,
        remoteDataSource: WebSocketRepository,
        @ApplicationContext context: Context
    ): Repository {
        return Repository(localDataSource, remoteDataSource, context)
    }
}