package com.example.hw_networking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw_networking.R
import com.example.hw_networking.databinding.FragmentItemMovieListBinding
import com.example.hw_networking.movies.RemoteMovie
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MovieDelegateAdapter(private val onItemClick: (itemMovie: RemoteMovie) -> Unit) :
    AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieDelegateAdapter.MovieHolder>() {

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): MovieHolder {
        return MovieHolder(
            FragmentItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(
        item: RemoteMovie,
        holder: MovieHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MovieHolder(
        binding: FragmentItemMovieListBinding,
        onItemClick: (itemMovie: RemoteMovie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val posterView = binding.posterImageView
        private val titleView = binding.titleTextView
        private val yearView = binding.yearTextView
        private val typeView = binding.typeTextView
        private val imdbIDView = binding.imdbIDTextView

        private var itemMovie: RemoteMovie? = null

        init {
            binding.root.setOnClickListener {
                itemMovie?.let {
                    onItemClick(it)
                }
            }
        }

        fun bind(item: RemoteMovie) {
            itemMovie = item
            posterView.load(item.poster) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.loading)
            }
            titleView.text = item.title
            "Year: ${item.year}".also { yearView.text = it }
            "Type: ${item.type}".also { typeView.text = it }
            "ID: ${item.imdbID}".also { imdbIDView.text = it }
        }
    }
}

