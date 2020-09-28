package com.example.firstapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmsDao {
    @Query("SELECT * FROM ems_app_data")
    fun getAll(): List<SearchHistoryItem>

    @Query("SELECT * FROM ems_app_data WHERE ems_id LIKE :emsId LIMIT 1")
    fun findByEmsId(emsId: String): SearchHistoryItem

    @Insert
    fun insertAll(vararg items: SearchHistoryItem)

    @Delete
    fun delete(item: SearchHistoryItem)
}