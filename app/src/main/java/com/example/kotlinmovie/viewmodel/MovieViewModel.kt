package com.example.kotlinmovie.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.kotlinmovie.data.MovieRepository
import com.example.kotlinmovie.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    val readMovieByDatabase = repository.getMoviesByDatabase().asLiveData()

    init {
        fetchMovies()
    }

    private fun fetchMovies() = viewModelScope.launch {
        try {
            val result = repository.getMovies()
            _movieList.value = result

            if (_movieList.value!!.isNotEmpty()) {
                insertMovie(result)
            }


        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun insertMovie(movie: List<Movie>) = viewModelScope.launch(Dispatchers.IO) {
        repository.addMoviesByDatabase(movie)
    }

}