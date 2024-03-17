package com.example.movietrailer.data.api

import com.example.movietrailer.data.model.TmdbVideos

class TmdbRepository {
    suspend fun getTmdbVideos(movieId: Int): TmdbVideos? {
        return TMDBRetrofit.retrofit?.create(MovieService::class.java)
            ?.getTMDBVideos(movieId = movieId)
    }
}