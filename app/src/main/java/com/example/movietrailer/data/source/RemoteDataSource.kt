package com.example.movietrailer.data.source

import com.example.movietrailer.data.source.model.TmdbMovies
import com.example.movietrailer.data.source.model.TmdbVideos

class RemoteDataSource {
    suspend fun getTmdbVideos(movieId: Long): TmdbVideos? {
        return TmdbRetrofit.retrofit?.create(MovieService::class.java)
            ?.getTmdbVideos(movieId = movieId)
    }

    suspend fun getTmdbMovies(): TmdbMovies? {
        return TmdbRetrofit.retrofit?.create(MovieService::class.java)
            ?.getPopularMovies()
    }
}