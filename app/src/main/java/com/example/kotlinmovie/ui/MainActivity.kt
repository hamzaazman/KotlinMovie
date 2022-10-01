package com.example.kotlinmovie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinmovie.adapter.MovieAdapter
import com.example.kotlinmovie.databinding.ActivityMainBinding
import com.example.kotlinmovie.model.Movie
import com.example.kotlinmovie.ui.DetailActivity.Companion.EXTRA_MOVIE
import com.example.kotlinmovie.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel

    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                goToDetail(movie)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        setupRecyclerView()

        readDatabase()
    }

    private fun readDatabase() {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.readMovieByDatabase.observe(this@MainActivity, Observer { listMovie ->
            if (listMovie.isNotEmpty()) {
                Log.d("Database", "readDatabase called!")
                movieAdapter.differ.submitList(listMovie)
            } else {
                requestApiData()
            }
            binding.progressBar.visibility = View.GONE

        })

    }

    private fun requestApiData() {
        Log.d("Retrofit", "requestApiData called!")
        binding.progressBar.visibility = View.VISIBLE
        viewModel.movieList.observe(this, Observer {
            movieAdapter.differ.submitList(it)
        })
        binding.progressBar.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter
            setHasFixedSize(true)

        }
    }

    private fun goToDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_MOVIE, movie)
        }
        startActivity(intent)
    }
}