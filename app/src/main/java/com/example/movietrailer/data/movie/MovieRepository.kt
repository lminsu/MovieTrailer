package com.example.movietrailer.data.movie

import com.example.movietrailer.data.tmdbSource.RemoteDataSource
import com.example.movietrailer.data.tmdbSource.model.TmdbMovie
import com.example.movietrailer.data.tmdbSource.model.TmdbMovies

class MovieRepository {
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()

    suspend fun getMovies(): List<Movie>? {
        return remoteDataSource.getTmdbMovies()?.toMovies()
    }

    private fun TmdbMovies.toMovies(): List<Movie>? {
        return getTmdbMovies()
            ?.map { it.toMovie() }
            ?.filterNotNull()
    }

    private fun TmdbMovie.toMovie(): Movie? {
        return Movie(
            id = id ?: return null,
            title = title,
            overview = overview,
            releaseDate = releaseDate,
            posterPath = makePosterPathWithBaseUrl()
        )
    }
}