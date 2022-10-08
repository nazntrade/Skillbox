package com.skillbox.hw_multithreading.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.skillbox.hw_multithreading.R
import com.skillbox.hw_multithreading.databinding.MovieItemBinding
import com.skillbox.hw_multithreading.networking.Movie

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieHolder>(MovieDiffUtilCallback()) {

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_item, parent, false)
        return MovieHolder(MovieItemBinding.bind(view))
    }


    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindMovie(getItem(position))
    }

    class MovieHolder(
        binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val titleMovieTextView = binding.movieTitleTextView
        private val yearMovieTextView = binding.movieYearTextView
        private val imageMovie = binding.movieImage
        private val countryTextView = binding.movieCountryTextView
        private val ratingTextView = binding.movieImdbRatingTextView

        fun bindMovie(movies: Movie) {
            imageMovie.load(movies.poster) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.loading)
            }
            movies.title.also { titleMovieTextView.text = it }
            "Year: ${movies.year}".also { yearMovieTextView.text = it }
            "Country: ${movies.country}".also { countryTextView.text = it }
            "Rating IMDb: ${movies.imdbRating}".also { ratingTextView.text = it }
        }
    }
}