package com.example.kotlinmovie.data

import com.example.kotlinmovie.data.database.MovieDao
import com.example.kotlinmovie.data.network.MovieApi
import com.example.kotlinmovie.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi, private val movieDao: MovieDao
) {
    suspend fun getMovies() = movieApi.getMovies()

    fun getMoviesByDatabase() = movieDao.getMovies()

    suspend fun addMoviesByDatabase(movie: List<Movie>) = movieDao.addMovies(movie)
}