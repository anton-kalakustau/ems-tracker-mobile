package com.example.firstapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SearchHistoryItem::class), version = 1)
abstract class EmsDatabase: RoomDatabase() {
    abstract fun emsDao(): EmsDao
}