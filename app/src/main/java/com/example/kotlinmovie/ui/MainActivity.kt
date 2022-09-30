package com.example.kotlinmovie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinmovie.adapter.MovieAdapter
import com.example.kotlinmovie.databinding.ActivityMainBinding
import com.example.kotlinmovie.model.Movie
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

        setupViewModelAndObserve()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = movieAdapter
        }
    }

    private fun setupViewModelAndObserve() {
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.movieList.observe(this, Observer {
            movieAdapter.differ.submitList(it)
        })
    }

    private fun goToDetail(movie: Movie) {

    }
}