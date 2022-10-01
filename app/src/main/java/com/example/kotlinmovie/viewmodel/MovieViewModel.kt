package com.example.kotlinmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmovie.data.MovieRepository
import com.example.kotlinmovie.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    init {
        fetchMovies()
    }

    private fun fetchMovies() = viewModelScope.launch {
        try {
            val result = repository.getMovies()
            withContext(Dispatchers.Main) {
                _movieList.value = result
            }

        } catch (e: Exception) {
            e.stackTrace
        }
    }

}