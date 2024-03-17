package com.example.movietrailer.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movietrailer.ui.main.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class YouTubePlayerController(
    viewLifecycleOwner: LifecycleOwner,
    viewModelStoreOwner: ViewModelStoreOwner,
    private val playerView: YouTubePlayerView,
) {

    private val mainViewModel by lazy {
        ViewModelProvider(owner = viewModelStoreOwner).get(
            MainViewModel::class.java
        )
    }

    init {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.videoKey.filterNotNull()
                        .collectLatest { startPlayer(videoId = it) }
                }
            }
        }
    }

    private fun startPlayer(videoId: String) {
        addPlayerListener(videoId)
    }

    private fun addPlayerListener(videoId: String) {
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId = videoId, startSeconds = 0f)
            }
        })
    }
}