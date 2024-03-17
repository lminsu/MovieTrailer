package com.example.movietrailer.data

import com.example.movietrailer.data.source.RemoteDataSource
import com.example.movietrailer.data.source.model.TmdbMovie
import com.example.movietrailer.data.source.model.TmdbVideo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VideoRepository {
    private val remoteDataSource: RemoteDataSource by lazy { RemoteDataSource() }

    private val _video = MutableStateFlow<Video?>(value = null)
    val video: StateFlow<Video?> = _video

    suspend fun getMostPopularVideo(): Video? {
        if (_video.value == null) {
            with(remoteDataSource) {
                val tmdbMovie = getTmdbMovies()?.getMostPopularTmdbMovie()
                val id = tmdbMovie?.id
                val tmdbVideo = if (id != null) {
                    getTmdbVideos(movieId = id)?.getTrailer()
                } else {
                    null
                }
                if (id != null && tmdbVideo != null) {
                    _video.value = makeVideo(id = id, tmdbMovie = tmdbMovie, tmdbVideo = tmdbVideo)
                }
            }
        }

        return _video.value
    }

    private fun makeVideo(id: Long, tmdbMovie: TmdbMovie, tmdbVideo: TmdbVideo): Video {
        return Video(
            id = id,
            title = tmdbMovie.title,
            key = tmdbVideo.key,
            releaseData = tmdbMovie.releaseDate,
            overview = tmdbMovie.overview,
            popularity = tmdbMovie.popularity
        )
    }
}