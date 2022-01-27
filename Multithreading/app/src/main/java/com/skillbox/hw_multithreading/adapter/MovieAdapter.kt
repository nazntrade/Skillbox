package com.skillbox.hw_multithreading.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.hw_multithreading.R
import com.skillbox.hw_multithreading.databinding.MovieItemBinding
import com.skillbox.hw_multithreading.networking.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return MovieHolder(MovieItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movieBound = movies[position]
        holder.bindMovie(movieBound)
    }

    override fun getItemCount(): Int = movies.size

    class MovieHolder(
        binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val titleMovieTextView = binding.movieTitleTextView
        private val yearMovieTextView = binding.movieYearTextView

        fun bindMovie(movies: Movie) {
            "Title: ${movies.title}".also { titleMovieTextView.text = it }
            "year: ${movies.year}".also { yearMovieTextView.text = it }
        }
    }
}