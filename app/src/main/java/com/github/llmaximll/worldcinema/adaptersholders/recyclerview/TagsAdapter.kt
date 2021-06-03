package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.dataclasses.network.Tag

class TagsAdapter(private val tagsList: List<Tag>) : RecyclerView.Adapter<TagsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_tags_view, parent, false)
        return TagsHolder(view)
    }

    override fun onBindViewHolder(holder: TagsHolder, position: Int) {
        val tag = tagsList[position]
        holder.bind(tag)
    }

    override fun getItemCount(): Int = tagsList.size
}