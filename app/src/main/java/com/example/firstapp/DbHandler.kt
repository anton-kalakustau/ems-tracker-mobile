package com.example.firstapp

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room

object DbHandler {
    var db: EmsDatabase? = null

    fun initialize(appContext: Context) {
        db = Room.databaseBuilder(
            appContext,
            EmsDatabase::class.java, "ems-search-database"
        ).build()
    }

    public class GetByIdExecutor : AsyncTask<String, Void, SearchHistoryItem?>(){
        override fun doInBackground(vararg emsId: String): SearchHistoryItem? {
            return db?.emsDao()?.findByEmsId(emsId[0]);
        }
    }

    public class InsertExecutor : AsyncTask<SearchHistoryItem, Void, Int>(){
        override fun doInBackground(vararg emsId: SearchHistoryItem): Int {
            db?.emsDao()?.insertAll(emsId[0]);
            return 0;
        }
    }

    public class GetAllExecutor : AsyncTask<Void, Void, List<SearchHistoryItem>?>(){
        override fun doInBackground(vararg void: Void): List<SearchHistoryItem>? {
            return db?.emsDao()?.getAll();
        }
    }

}