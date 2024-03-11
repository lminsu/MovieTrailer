package com.example.movietrailer.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietrailer.data.api.TMDBRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository by lazy { TMDBRepository() }


    fun loadTMDBVideos() {
        viewModelScope.launch {
            try {
                val TMDBVideos = repository.getTMDBVideos(movieId = 1096197)
                Log.d(TAG, "getTMDBVideos succeeds, $TMDBVideos")
            } catch (e: Exception) {
                Log.e(TAG, "getTMDBVideos fails, $e")
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}