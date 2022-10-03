package com.example.kotlinmovie.viewmodel

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.*
import com.example.kotlinmovie.data.MovieRepository
import com.example.kotlinmovie.model.Movie
import com.example.kotlinmovie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieList = MutableLiveData<Resource<List<Movie>>>()
    val movieList: LiveData<Resource<List<Movie>>>
        get() = _movieList

    val readMovieByDatabase = repository.getMoviesByDatabase().asLiveData()

    init {
        fetchMovies()
    }

    private fun fetchMovies() = viewModelScope.launch {
        _movieList.postValue(Resource.loading(null))
        try {
            val result = repository.getMovies()
            _movieList.value = Resource.success(result)

            if (_movieList.value != null) {
                insertMovie(result)
            }
        } catch (e: Exception) {
            Resource.error("Something went wrong", e.localizedMessage)
        }
    }

    private fun insertMovie(movie: List<Movie>) = viewModelScope.launch(Dispatchers.IO) {
        repository.addMoviesByDatabase(movie)
    }

}