package com.example.movietrailer.data.tmdbSource

import com.example.movietrailer.data.tmdbSource.model.TmdbMovies
import com.example.movietrailer.data.tmdbSource.model.TmdbVideos

class RemoteDataSource {
    suspend fun getTmdbVideos(movieId: Long): TmdbVideos? {
        return TmdbRetrofit.retrofit?.create(TmdbService::class.java)
            ?.getTmdbVideos(movieId = movieId)
    }

    suspend fun getTmdbMovies(): TmdbMovies? {
        return TmdbRetrofit.retrofit?.create(TmdbService::class.java)
            ?.getPopularMovies()
    }
}