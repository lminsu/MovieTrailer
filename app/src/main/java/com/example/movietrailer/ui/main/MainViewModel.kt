package com.example.movietrailer.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietrailer.data.Video
import com.example.movietrailer.data.VideoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    val video: StateFlow<Video?> = repository.video
    val videoKey: StateFlow<String?> = video.map { it?.key }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    fun loadTMDBVideos() {
        viewModelScope.launch {
            try {
                val video = repository.getMostPopularVideo()
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