package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.dataclasses.network.EpisodeInfo

class EpisodesAdapter(private val episodesList: List<EpisodeInfo>)
    : RecyclerView.Adapter<EpisodesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_episodes_view, parent, false)
        return EpisodesHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodesHolder, position: Int) {
        val episodeInfo = episodesList[position]
        holder.bind(episodeInfo, position)
    }

    override fun getItemCount(): Int = episodesList.size
}