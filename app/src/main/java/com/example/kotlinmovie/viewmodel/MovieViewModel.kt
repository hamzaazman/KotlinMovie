package com.example.kotlinmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinmovie.data.MovieRepository
import com.example.kotlinmovie.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    private val disposable = CompositeDisposable()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        disposable.add(
            repository.getMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Movie>>() {
                    override fun onSuccess(t: List<Movie>) {
                        _movieList.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.stackTrace
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}