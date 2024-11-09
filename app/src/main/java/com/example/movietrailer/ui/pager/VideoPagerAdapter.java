package com.example.movietrailer.ui.pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movietrailer.ui.main.MainFragment;

import java.util.List;

public class VideoPagerAdapter extends FragmentStateAdapter {
    private static final int MAX_NUM_PAGES = 5;
    private final List<Long> movieIds;

    public VideoPagerAdapter(@NonNull Fragment fragment, List<Long> movieIds) {
        super(fragment);
        this.movieIds = movieIds;
    }

    @Override
    public int getItemCount() {
        return movieIds.size() >= MAX_NUM_PAGES ? MAX_NUM_PAGES : movieIds.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Long movieId = (position >= 0 && position < movieIds.size()) ? movieIds.get(position) : null;
        if (movieId != null) {
            return MainFragment.Companion.newInstance(movieId);
        } else {
            return MainFragment.Companion.newInstance(0L);
        }
    }
}