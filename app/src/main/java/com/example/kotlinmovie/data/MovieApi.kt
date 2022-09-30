package com.example.kotlinmovie.data

import com.example.kotlinmovie.model.Movie
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.http.GET

interface MovieApi {

    @GET("hamzaazman/Dataset/main/movies.json")
    fun getMovies(): Single<List<Movie>>

}