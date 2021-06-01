package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R

class TrendsAdapter(private val moviesList: List<String>) : RecyclerView.Adapter<TrendsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_trends_view, parent, false)
        return TrendsHolder(view)
    }

    override fun onBindViewHolder(holder: TrendsHolder, position: Int) {
        val posterUrl = moviesList[position]
        holder.bind(posterUrl)
    }

    override fun getItemCount(): Int = moviesList.size
}