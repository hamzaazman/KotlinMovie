package com.example.kotlinmovie.data.network

import com.example.kotlinmovie.model.Movie
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("hamzaazman/Dataset/main/movies.json")
    suspend fun getMovies(): List<Movie>

}