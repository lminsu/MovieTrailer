package com.example.movietrailer.ui.pager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movietrailer.data.movie.MovieRepository;
import com.example.movietrailer.databinding.FragmentPagerBinding;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.FlowKt;

public class PagerFragment extends Fragment {

    private static final String TAG = PagerFragment.class.getSimpleName();

    private PagerViewModel viewModel;
    private FragmentPagerBinding _binding;

    private FragmentPagerBinding getBinding() {
        return _binding;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, TAG + " > " + hashCode() + " > onCreateView");
        _binding = FragmentPagerBinding.inflate(inflater, container, false);
        return getBinding().getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, TAG + " > " + hashCode() + " > onViewCreated");
        viewModel = makePagerViewModel();
        collects();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, TAG + " > " + hashCode() + " > onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, TAG + " > " + hashCode() + " > onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, TAG + " > " + hashCode() + " > onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, TAG + " > " + hashCode() + " > onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, TAG + " > " + hashCode() + " > onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, TAG + " > " + hashCode() + " > onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, TAG + " > " + hashCode() + " > onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, TAG + " > " + hashCode() + " > onDetach");
    }

    private void initVideoPager(java.util.List<Long> movieIds) {
        getBinding().viewPageVideo.setAdapter(new VideoPagerAdapter(this, movieIds));
    }

    private void collects() {
        CoroutineScope lifecycleScope = LifecycleOwnerKt.getLifecycleScope(this);
        BuildersKt.launch(lifecycleScope, EmptyCoroutineContext.INSTANCE, CoroutineStart.DEFAULT,
                (outerScope, outerContinuation) -> {
                    RepeatOnLifecycleKt.repeatOnLifecycle(this, Lifecycle.State.STARTED, (scope, continuation) -> {
                        CoroutineScope scopeForFlow = new CoroutineScope() {
                            @NonNull
                            @Override
                            public CoroutineContext getCoroutineContext() {
                                return EmptyCoroutineContext.INSTANCE;
                            }
                        };
                        BuildersKt.launch(scopeForFlow, EmptyCoroutineContext.INSTANCE, CoroutineStart.DEFAULT,
                                (innerScope, innerContinuation) -> {

                                    FlowKt.collectLatest(FlowKt.filterNotNull(viewModel.getMovieIds()), (movieIds, collectContinuation) -> {
                                        initVideoPager(movieIds);
                                        return Unit.INSTANCE;
                                    }, outerContinuation);

                                    return Unit.INSTANCE;
                                });

                        return Unit.INSTANCE;
                    }, outerContinuation);

                    return Unit.INSTANCE;
                });
    }

    private PagerViewModel makePagerViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PagerViewModel(new MovieRepository());
            }
        }).get(PagerViewModel.class);
    }
}
