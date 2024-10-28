package com.example.movietrailer.ui.pager

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
import com.example.movietrailer.data.movie.Movie
import com.example.movietrailer.data.movie.MovieRepository
import com.example.movietrailer.databinding.FragmentPagerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PagerFragment : Fragment() {

	companion object {
		private val TAG: String = PagerFragment::class.java.simpleName
	}

	private val viewModel: PagerViewModel by lazy { makePagerViewModel() }
	private var _binding: FragmentPagerBinding? = null
	private val binding: FragmentPagerBinding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		Log.d(TAG, "$TAG > ${hashCode()} > onCreateView")
		_binding = FragmentPagerBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		Log.d(TAG, "$TAG > ${hashCode()} > onViewCreated")
		collects()
		viewModel.loadMovies()
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

	private fun initVideoPager(movieIds: List<Long>) {
		binding.viewPageVideo.adapter = VideoPagerAdapter(
			fragment = this,
			movieIds = movieIds
		)
	}

	private fun collects() {
		lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch {
					viewModel.movieIds.filterNotNull().collectLatest {
						initVideoPager(movieIds = it)
					}
				}
			}
		}
	}

	fun getMovie(movieId: Long): Movie? {
		return viewModel.getMovie(movieId = movieId)
	}

	private fun makePagerViewModel(): PagerViewModel {
		return ViewModelProvider(owner = this, object : ViewModelProvider.Factory {
			override fun <T : ViewModel> create(modelClass: Class<T>): T {
				@Suppress("UNCHECKED_CAST")
				return PagerViewModel(repository = MovieRepository()) as T
			}
		})[PagerViewModel::class.java]
	}

}
