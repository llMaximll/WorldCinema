package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.fragments.ViewPagerMainScreenTrendsFragment

class TrendsAdapter(private val callbacks: ViewPagerMainScreenTrendsFragment.Callbacks,
                    private val moviesList: List<MovieInfo>)
    : RecyclerView.Adapter<TrendsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_trends_view, parent, false)
        return TrendsHolder(callbacks, view)
    }

    override fun onBindViewHolder(holder: TrendsHolder, position: Int) {
        val movieInfo = moviesList[position]
        holder.bind(movieInfo.movieId, movieInfo.poster)
    }

    override fun getItemCount(): Int = moviesList.size
}