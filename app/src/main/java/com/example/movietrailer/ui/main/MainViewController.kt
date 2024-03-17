package com.example.movietrailer.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.movietrailer.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class MainViewController(
    private val binding: FragmentMainBinding,
    private val viewModelStoreOwner: ViewModelStoreOwner,
    private val viewLifecycleOwner: LifecycleOwner,
) {

    private val viewModel by lazy { ViewModelProvider(owner = viewModelStoreOwner)[MainViewModel::class.java] }

    init {
        collects()
    }

    private fun collects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.backgroundImgSrc.filterNotNull().collectLatest {
                        loadBackgroundImage(imgSrc = it)
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

    private fun loadBackgroundImage(imgSrc: String) {
        with(binding.ivBg) {
            Glide.with(this)
                .load(imgSrc)
                .centerCrop()
                .into(this)
        }
    }

    fun initStatusBarHeight(height: Int) {
        binding.viewStatusBar.layoutParams.height = height
    }
}