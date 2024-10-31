package com.example.movietrailer.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.movietrailer.data.video.VideoRepository
import com.example.movietrailer.databinding.FragmentMainBinding
import com.example.movietrailer.ui.pager.PagerFragment
import com.example.movietrailer.ui.pager.PagerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

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

	private var _binding: FragmentMainBinding? = null
	private val binding: FragmentMainBinding get() = _binding!!

	private val viewModel: MainViewModel by lazy { makeMainViewModel() }
	private val pagerViewModel: PagerViewModel? by lazy {
		val pagerFragment = parentFragment as? PagerFragment ?: return@lazy null
		ViewModelProvider(owner = pagerFragment)[PagerViewModel::class.java]
	}
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
		initViews()
		collects()
	}

	private fun initViews() {
		lifecycle.addObserver(binding.youtubePlayerView)
		binding.viewStatusBar.layoutParams.height = getStatusBarHeight()
	}

	private fun collects() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch {
					viewModel.videoKey.filterNotNull().collectLatest { startPlayer(videoId = it) }
				}
				launch {
					viewModel.backgroundImgSrc.filterNotNull().collectLatest {
						Glide.with(binding.ivBg).load(it).centerCrop().into(binding.ivBg)
					}
				}

				launch {
					viewModel.title.filterNotNull().collectLatest {
						binding.title.text = it
					}
				}

				launch {
					viewModel.overview.filterNotNull().collectLatest {
						binding.tvOverview.text = it
					}
				}

				launch {
					viewModel.releaseData.filterNotNull().collectLatest {
						binding.tvReleaseDate.text = it
					}
				}
			}
		}

	}

	private fun startPlayer(videoId: String) {
		binding.youtubePlayerView.addYouTubePlayerListener(object :
			AbstractYouTubePlayerListener() {
			override fun onReady(youTubePlayer: YouTubePlayer) {
				youTubePlayer.loadVideo(videoId = videoId, startSeconds = 0f)
			}
		})

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


	@SuppressLint("InternalInsetResource", "DiscouragedApi")
	fun getStatusBarHeight(): Int {
		var result = 0
		val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
		if (resourceId > 0) {
			result = resources.getDimensionPixelSize(resourceId)
		}
		return result
	}

	// todo: hilt 사용
	@Suppress("UNCHECKED_CAST")
	private fun makeMainViewModel(): MainViewModel {
		return ViewModelProvider(owner = this, object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				return MainViewModel(
					repository = VideoRepository(),
					movie = pagerViewModel?.getMovie(movieId)
				) as T
			}
		})[MainViewModel::class.java]
	}
}