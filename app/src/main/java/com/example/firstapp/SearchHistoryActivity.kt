package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class SearchHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_history)

        val data = DbHandler.GetAllExecutor().execute().get();

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = HistoryViewAdapter(data!!.toTypedArray())

        findViewById<RecyclerView>(R.id.historyList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}
