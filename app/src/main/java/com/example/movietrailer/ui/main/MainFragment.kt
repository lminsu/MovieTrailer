package com.example.movietrailer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietrailer.data.VideoRepository
import com.example.movietrailer.databinding.FragmentMainBinding
import com.example.movietrailer.ui.YouTubePlayerController

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(owner = this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository = VideoRepository()) as T
            }
        })[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initYouTubePlayerController()
        viewModel.loadTMDBVideos()
    }

    private fun initYouTubePlayerController() {
        with(binding.youtubePlayerView) {
            YouTubePlayerController(
                viewLifecycleOwner = viewLifecycleOwner,
                viewModelStoreOwner = this@MainFragment,
                playerView = this
            )
            lifecycle.addObserver(observer = this)
        }
    }
}