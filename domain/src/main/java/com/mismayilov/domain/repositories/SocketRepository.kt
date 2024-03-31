package com.abbtech.firstabbtechapp.domain.repositories

interface SocketRepository {
    suspend fun fetchData()
}