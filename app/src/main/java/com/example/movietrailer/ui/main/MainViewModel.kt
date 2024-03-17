package com.example.movietrailer.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietrailer.data.movie.Movie
import com.example.movietrailer.data.video.Video
import com.example.movietrailer.data.video.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    private val _video = MutableStateFlow<Video?>(value = null)
    val video: StateFlow<Video?> = _video

    val videoKey: StateFlow<String?> = video.map { it?.key }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
    val backgroundImgSrc: StateFlow<String?> = video.map { it?.posterPath }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
    val title: StateFlow<String?> = video.map { it?.title }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
    val releaseData: StateFlow<String?> = video.map { it?.releaseData }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
    val overview: StateFlow<String?> = video.map { it?.overview }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    fun loadVideo(movie: Movie) {
        viewModelScope.launch {
            try {
                _video.value = repository.getVideo(movie = movie)
                Log.d(TAG, "getTMDBVideos succeeds, $video")
            } catch (e: Exception) {
                Log.e(TAG, "getTMDBVideos fails, $e")
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}