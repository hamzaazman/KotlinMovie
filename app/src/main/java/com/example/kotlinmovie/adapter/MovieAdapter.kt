package com.example.kotlinmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmovie.R
import com.example.kotlinmovie.databinding.MovieItemRowBinding
import com.example.kotlinmovie.model.Movie

class MovieAdapter(private val clickListener: MovieClickListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val binding: MovieItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imagePath = "https://image.tmdb.org/t/p/w185/"
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title

            Glide.with(itemView.context)
                .load("$imagePath${movie.poster_path}")
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(binding.movieImageView)
        }
    }

    private val movieDiffUtilCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, movieDiffUtilCallback)

    interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovieList = differ.currentList[position]
        holder.bind(currentMovieList)

        holder.itemView.setOnClickListener {
            clickListener.onMovieClick(currentMovieList)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}