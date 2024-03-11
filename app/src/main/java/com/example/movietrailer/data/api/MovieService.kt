package com.example.movietrailer.data.api

import com.example.movietrailer.data.model.TMDBVideos
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("3/movie/{movie_id}/videos")
    suspend fun getTMDBVideos(@Path("movie_id") movieId: Int): TMDBVideos
}