package com.example.movietrailer.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movietrailer.ui.main.MainFragment

class VideoPagerAdapter(
    fragment: Fragment,
    val movieIds: List<Long>
) : FragmentStateAdapter(fragment) {
    companion object {
        private const val MAX_NUM_PAGES = 5
    }

    override fun getItemCount(): Int {
        return if (movieIds.size >= MAX_NUM_PAGES) {
            MAX_NUM_PAGES
        } else {
            movieIds.size
        }
    }

    override fun createFragment(position: Int): Fragment {
        val movieId = movieIds.getOrNull(position)
        return if (movieId != null) {
            MainFragment.newInstance(movieId = movieId)
        } else {
            MainFragment.newInstance(movieId = 0)
        }
    }
}