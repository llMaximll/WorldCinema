package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R

class FramesAdapter(private val framesList: List<String>) : RecyclerView.Adapter<FramesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FramesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_frames_view, parent, false)
        return FramesHolder(view)
    }

    override fun onBindViewHolder(holder: FramesHolder, position: Int) {
        val frameURL = framesList[position]
        holder.bind(frameURL)
    }

    override fun getItemCount(): Int = framesList.size
}