package com.example.movietrailer.data.tmdbSource.model

import com.google.gson.annotations.SerializedName

data class TmdbMovies(
    val page: Long?,
    val results: List<TmdbMovie>?,
) {
    fun getTmdbMovies(): List<TmdbMovie>? {
        return results
    }
}

data class TmdbMovie(
    val id: Long?,
    val title: String?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
) {
    fun makePosterPathWithBaseUrl(): String {
        return BASE_IMAGE_URL + posterPath
    }
}

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original"
