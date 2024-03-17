package com.example.movietrailer.data

data class Video(
    val id: Long,
    val title: String?,
    val key: String?,
    val releaseData: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
)