package com.example.firstapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class TrackingViewAdapter(private val myDataset: Array<Array<String>>) :
    RecyclerView.Adapter<TrackingViewAdapter.TrackingViewHolder>()  {

    class TrackingViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView

        init {
            textView = view.findViewById<TextView>(R.id.textView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TrackingViewAdapter.TrackingViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_item, parent, false)

        return TrackingViewHolder(view)

    }

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) {
        holder.textView.text = myDataset[position].joinToString(System.lineSeparator())
    }

    override fun getItemCount() = myDataset.size
}