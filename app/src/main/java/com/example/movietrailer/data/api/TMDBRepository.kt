package com.example.movietrailer.data.api

import com.example.movietrailer.data.model.TMDBVideos

class TMDBRepository {
    suspend fun getTMDBVideos(movieId: Int): TMDBVideos? {
        return TMDBRetrofit.retrofit?.create(MovieService::class.java)
            ?.getTMDBVideos(movieId = movieId)
    }
}