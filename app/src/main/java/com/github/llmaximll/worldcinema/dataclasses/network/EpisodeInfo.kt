package com.github.llmaximll.worldcinema.dataclasses.network

data class EpisodeInfo(
    val episodeId: String,
    val name: String,
    val description: String,
    val director: String,
    val stars: List<String>,
    val year: String,
    val images: List<String>,
    val runtime: String,
    val preview: String,
    val moviesId: String)