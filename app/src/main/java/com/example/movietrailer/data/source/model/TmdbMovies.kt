package com.example.movietrailer.data.source.model

import com.google.gson.annotations.SerializedName

data class TmdbMovies(
    val page: Long?,
    val results: List<TmdbMovie>?,
) {
    fun getMostPopularTmdbMovie(): TmdbMovie? {
        return results?.get(0)
    }
}

data class TmdbMovie(
    val id: Long?,
    val title: String?,
    val popularity: Double?,
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
