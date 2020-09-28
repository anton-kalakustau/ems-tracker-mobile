package com.example.firstapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ems_app_data")
data class SearchHistoryItem(
    @PrimaryKey @ColumnInfo(name = "ems_id") val searchId: String,
    @ColumnInfo(name = "search_date") val searchDate: String,
    @ColumnInfo(name = "result_hash") val resultHash: String
)