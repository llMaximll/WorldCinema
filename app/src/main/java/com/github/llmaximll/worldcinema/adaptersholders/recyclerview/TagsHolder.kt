package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.dataclasses.network.Tag

class TagsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tagTextView: TextView = itemView.findViewById(R.id.tag_textView)

    fun bind(tag: Tag) {
        tagTextView.text = tag.tagName
    }
}