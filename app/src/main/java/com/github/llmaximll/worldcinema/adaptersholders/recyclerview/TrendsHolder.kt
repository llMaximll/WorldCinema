package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.llmaximll.worldcinema.R

class TrendsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)

    fun bind(posterUrl: String) {
        Glide.with(itemView)
            .load("http://cinema.areas.su/up/images/$posterUrl")
            .placeholder(R.drawable.logo_foreground)
            .error(R.drawable.logo_foreground)
            .fallback(R.drawable.logo_foreground)
            .into(imageView)
    }
}