package com.example.movietrailer.data.model

data class TmdbVideos(
    val id: Int?,
    val results: List<TmdbVideosResult>
)

data class TmdbVideosResult(
    val name: String?,
    val key: String?,
    val site: String?,
    val size: Int,
    val type: String,
    val id: String,
)
