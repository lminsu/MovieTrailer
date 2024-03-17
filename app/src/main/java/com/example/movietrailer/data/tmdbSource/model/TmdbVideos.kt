package com.example.movietrailer.data.tmdbSource.model

const val TRAILER = "Trailer"

data class TmdbVideos(
    val id: Int?,
    val results: List<TmdbVideo>?,
) {
    fun getTrailer(): TmdbVideo? {
        return results?.firstOrNull {
            it.isTrailer()
        }
    }
}

data class TmdbVideo(
    val key: String?,
    val type: String?,
) {
    fun isTrailer(): Boolean {
        return type == TRAILER
    }
}