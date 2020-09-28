package com.example.firstapp

import android.content.Intent
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class HistoryViewAdapter(private val myDataset: Array<SearchHistoryItem>) :
    RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>()  {

    var parentContext: ViewGroup? = null

    class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val SearchId: TextView
        val SearchDate: TextView

        init {
            SearchId = view.findViewById<TextView>(R.id.textSearchId)
            SearchDate = view.findViewById(R.id.testSearchDate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : HistoryViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_item, parent, false)

        this.parentContext = parent;

        return HistoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.SearchId.text = myDataset[position].searchId
        holder.SearchId.setMovementMethod(LinkMovementMethod.getInstance())
        holder.SearchId.setOnClickListener{
            val apiCallResult =  DataFetcher().execute(myDataset[position].searchId).get();
            val intent = Intent(this.parentContext?.context, SearchResultActivity::class.java).apply {
                putExtra(SEARCH_EMS_ID, apiCallResult)
            }
            this.parentContext?.context?.startActivity(intent)
        }
        holder.SearchDate.text = myDataset[position].searchDate
    }

    override fun getItemCount() = myDataset.size
}