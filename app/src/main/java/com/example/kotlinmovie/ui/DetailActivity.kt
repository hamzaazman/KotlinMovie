package com.example.kotlinmovie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kotlinmovie.R
import com.example.kotlinmovie.databinding.ActivityDetailBinding
import com.example.kotlinmovie.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_MOVIE = "movie"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w185/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent?.getParcelableExtra<Movie>(EXTRA_MOVIE)

        movie?.run {
            binding.detailTitle.text = title
            binding.detailOverview.text = overview
            binding.detailRelease.text = release_date.take(4)
            Glide.with(this@DetailActivity)
                .load("$IMAGE_URL$poster_path")
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(binding.detailImage)
        }
    }
}