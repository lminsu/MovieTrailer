package com.example.movietrailer.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietrailer.data.movie.Movie
import com.example.movietrailer.data.video.VideoRepository
import com.example.movietrailer.databinding.FragmentMainBinding
import com.example.movietrailer.ui.YouTubePlayerController
import com.example.movietrailer.ui.pager.PagerFragment

class MainFragment : Fragment() {

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Long) = MainFragment().apply {
            arguments = Bundle().apply {
                putLong(MOVIE_ID, movieId)
            }
        }
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private var movieId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getLong(MOVIE_ID)
        }

        viewModel = makeMainViewModel()
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
        initMainViewController()
        movieId?.let {
            val movie = getMovie(movieId = it) ?: return@let
            viewModel.loadVideo(movie = movie)
        }
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

    private fun initMainViewController() {
        MainViewController(
            binding = binding,
            viewModelStoreOwner = this@MainFragment,
            viewLifecycleOwner = viewLifecycleOwner
        ).run {
            initStatusBarHeight(height = getStatusBarHeight())
        }
    }


    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun getMovie(movieId: Long): Movie? {
        return (parentFragment as? PagerFragment)?.getMovie(movieId)
    }

    // todo: hilt 사용
    @Suppress("UNCHECKED_CAST")
    private fun makeMainViewModel(): MainViewModel {
        return ViewModelProvider(owner = this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repository = VideoRepository()) as T
            }
        })[MainViewModel::class.java]
    }
}