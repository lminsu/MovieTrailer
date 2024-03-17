package com.example.movietrailer.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietrailer.data.movie.Movie
import com.example.movietrailer.data.movie.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PagerViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>?>(value = null)
    val movies: StateFlow<List<Movie>?> = _movies
    val movieIds: StateFlow<List<Long>?> = movies.map { it?.map { movie -> movie.id } }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = null)

    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = repository.getMovies()
        }
    }

    fun getMovie(movieId: Long): Movie? {
        return movies.value?.firstOrNull { it.id == movieId }
    }
}