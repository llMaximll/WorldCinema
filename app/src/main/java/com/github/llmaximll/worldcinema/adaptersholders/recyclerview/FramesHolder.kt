package com.github.llmaximll.worldcinema.adaptersholders.recyclerview

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.llmaximll.worldcinema.R

class FramesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val frameImageView: ImageView = itemView.findViewById(R.id.frame_imageView)

    fun bind(frameURL: String) {
        Glide.with(itemView)
            .load("http://cinema.areas.su/up/images/$frameURL")
            .centerCrop()
            .placeholder(R.drawable.logo_foreground)
            .error(R.drawable.logo_foreground)
            .fallback(R.drawable.logo_foreground)
            .into(frameImageView)

    }
}