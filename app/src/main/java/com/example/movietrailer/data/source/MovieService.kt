package com.example.movietrailer.data.source

import com.example.movietrailer.data.source.model.TmdbMovies
import com.example.movietrailer.data.source.model.TmdbVideos
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("3/movie/{movie_id}/videos")
    suspend fun getTmdbVideos(@Path("movie_id") movieId: Long): TmdbVideos

    @GET("3/movie/popular")
    suspend fun getPopularMovies(): TmdbMovies
}