package com.example.movietrailer.data.model

data class TMDBVideos(
    val id: Int?,
    val results: List<TMDBVideosResult>
)

data class TMDBVideosResult(
    val name: String?,
    val key: String?,
    val site: String?,
    val size: Int,
    val type: String,
    val id: String,
)
