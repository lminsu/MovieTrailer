package com.example.movietrailer.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietrailer.data.api.TmdbRepository
import com.example.movietrailer.data.model.TmdbVideos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository by lazy { TmdbRepository() }

    private val _tmdbVideos = MutableStateFlow<TmdbVideos?>(value = null)
    val tmdbVideos: Flow<TmdbVideos?> = _tmdbVideos

    fun loadTMDBVideos() {
        viewModelScope.launch {
            try {
                val tmdbVideos = repository.getTmdbVideos(movieId = 1096197)
                Log.d(TAG, "getTMDBVideos succeeds, $tmdbVideos")
                this@MainViewModel._tmdbVideos.value = tmdbVideos
            } catch (e: Exception) {
                Log.e(TAG, "getTMDBVideos fails, $e")
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}