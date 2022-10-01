package com.example.kotlinmovie.data

import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getMovies() = movieApi.getMovies()
}