package com.example.movietrailer.data.movie

data class Movie(
    val id: Long,
    val title: String?,
    val releaseDate: String?,
    val overview: String?,
    val posterPath: String?,
)