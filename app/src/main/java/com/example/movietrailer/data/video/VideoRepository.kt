package com.example.movietrailer.data.video

import com.example.movietrailer.data.movie.Movie
import com.example.movietrailer.data.tmdbSource.RemoteDataSource
import com.example.movietrailer.data.tmdbSource.model.TmdbVideo

class VideoRepository {
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource() }

    suspend fun getVideo(movie: Movie): Video? {
        val tmdbVideo = remoteDataSource.getTmdbVideos(movieId = movie.id)?.getTrailer()
        return if (tmdbVideo != null) {
            makeVideo(id = movie.id, movie = movie, tmdbVideo = tmdbVideo)
        } else {
            null
        }
    }

    private fun makeVideo(id: Long, movie: Movie, tmdbVideo: TmdbVideo): Video {
        return Video(
            id = id,
            title = movie.title,
            key = tmdbVideo.key,
            releaseData = movie.releaseDate,
            overview = movie.overview,
            posterPath = movie.posterPath,
        )
    }
}