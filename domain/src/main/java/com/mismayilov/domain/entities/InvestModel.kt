package com.mismayilov.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "socket_model_table")
data class InvestModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "direction")
    @SerializedName("0") val direction: String,
    @ColumnInfo(name = "company_name")
    @SerializedName("1") val companyName: String,
    @ColumnInfo(name = "open_price")
    @SerializedName("2") val openPrice: String,
    @ColumnInfo(name = "high_price")
    @SerializedName("3") val highPrice: String,
    @ColumnInfo(name = "low_price")
    @SerializedName("4") val lowPrice: String,
    @ColumnInfo(name = "last_price")
    @SerializedName("5") val lastPrice: String,
    @ColumnInfo(name = "rating")
    @SerializedName("6") val rating: Int,
    @ColumnInfo(name = "timestamp")
    @SerializedName("7") val timestamp: String)