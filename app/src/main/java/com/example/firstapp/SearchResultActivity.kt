package com.example.firstapp

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class SearchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val data = intent.getSerializableExtra(SEARCH_EMS_ID) as Array<Array<String>>

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = TrackingViewAdapter(data)

        findViewById<RecyclerView>(R.id.resultsList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
