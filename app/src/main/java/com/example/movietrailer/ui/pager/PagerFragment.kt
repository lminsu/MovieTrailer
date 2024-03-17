package com.example.movietrailer.ui.pager

import android.os.Bundle
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
        fun newInstance() = PagerFragment()
    }

    private lateinit var viewModel: PagerViewModel
    private var _binding: FragmentPagerBinding? = null
    private val binding: FragmentPagerBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(owner = this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PagerViewModel(repository = MovieRepository()) as T
            }
        })[PagerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collects()
        viewModel.loadMovies()
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
}