package com.example.kotlinmovie.data

import com.example.kotlinmovie.model.Movie
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MovieApi {
    //coroutine branch' deyim. Ona göre operasyonları depiştir...
    @GET("hamzaazman/Dataset/main/movies.json")
    suspend fun getMovies(): List<Movie>

}