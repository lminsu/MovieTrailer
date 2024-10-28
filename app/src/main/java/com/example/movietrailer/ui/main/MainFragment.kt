package com.example.movietrailer.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
		private val TAG: String = MainFragment::class.java.simpleName
		private const val MOVIE_ID = "movie_id"

		fun newInstance(movieId: Long) = MainFragment().apply {
			arguments = Bundle().apply {
				putLong(MOVIE_ID, movieId)
			}
		}
	}

	private val viewModel: MainViewModel by lazy { makeMainViewModel() }
	private var _binding: FragmentMainBinding? = null
	private val binding: FragmentMainBinding get() = _binding!!
	private var movieId: Long? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			movieId = it.getLong(MOVIE_ID)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		Log.d(TAG, "$TAG > ${hashCode()} > onCreateView")
		_binding = FragmentMainBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		Log.d(TAG, "$TAG > ${hashCode()} > onViewCreated")
		initYouTubePlayerController()
		initMainViewController()
		movieId?.let {
			val movie = getMovie(movieId = it) ?: return@let
			viewModel.loadVideo(movie = movie)
		}
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		Log.d(TAG, "$TAG > ${hashCode()} > onAttach")
	}

	override fun onStart() {
		super.onStart()
		Log.d(TAG, "$TAG > ${hashCode()} > onStart")
	}

	override fun onResume() {
		super.onResume()
		Log.d(TAG, "$TAG > ${hashCode()} > onResume")
	}

	override fun onPause() {
		super.onPause()
		Log.d(TAG, "$TAG > ${hashCode()} > onPause")
	}

	override fun onStop() {
		super.onStop()
		Log.d(TAG, "$TAG > ${hashCode()} > onStop")
	}

	override fun onDestroyView() {
		super.onDestroyView()
		Log.d(TAG, "$TAG > ${hashCode()} > onDestroyView")
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "$TAG > ${hashCode()} > onDestroy")
	}

	override fun onDetach() {
		super.onDetach()
		Log.d(TAG, "$TAG > ${hashCode()} > onDetach")
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