package com.github.llmaximll.worldcinema.dataclasses.network

data class MovieInfo(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val images: List<String>,
    val poster: String,
    val tags: List<Tag>)