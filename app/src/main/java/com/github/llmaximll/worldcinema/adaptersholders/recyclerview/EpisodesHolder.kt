package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.dataclasses.network.EpisodeInfo

class EpisodesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.episode_imageView)
    private val titleTextView: TextView = itemView.findViewById(R.id.episode_title)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.description_textView)
    private val yearTextView: TextView = itemView.findViewById(R.id.year_textView)

    fun bind(episodeInfo: EpisodeInfo, position: Int) {
        //imageView
        val episodeImageURL = if (episodeInfo.images.size > position)
            episodeInfo.images[position]
        else
            ""
        Glide.with(itemView)
            .load("http://cinema.areas.su/up/images/$episodeImageURL")
            .centerCrop()
            .placeholder(R.drawable.logo_foreground)
            .error(R.drawable.logo_foreground)
            .fallback(R.drawable.logo_foreground)
            .into(imageView)

        titleTextView.text = episodeInfo.name
        descriptionTextView.text = episodeInfo.description
        yearTextView.text = episodeInfo.year
    }
}