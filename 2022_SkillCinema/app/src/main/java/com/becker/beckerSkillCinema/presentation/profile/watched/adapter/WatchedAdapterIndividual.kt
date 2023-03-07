package com.becker.beckerSkillCinema.presentation.profile.watched.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.databinding.ItemFilmBinding
import com.bumptech.glide.Glide

class WatchedAdapterIndividual(
    val onWatchedItemClick: (Movie) -> Unit
) :
    ListAdapter<Movie, WatchedHolderIndividual>(DiffUtilCallBackWatched()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedHolderIndividual {
        return WatchedHolderIndividual(
            binding = ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onWatchedItemClick = onWatchedItemClick
        )
    }

    override fun onBindViewHolder(holder: WatchedHolderIndividual, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}

class WatchedHolderIndividual(
    val binding: ItemFilmBinding,
    val onWatchedItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        with(binding) {
            Glide
                .with(itemFilmPoster.context)
                .load(item.posterUri)
                .centerCrop()
                .into(itemFilmPoster)

            itemFilmGenre.text = item.genre ?: ""
            itemFilmName.text = item.movieName ?: item.nameEn
            itemFilmRating.text = item.rating.toString()
        }
        binding.root.setOnClickListener {
            onWatchedItemClick(item)
        }
    }
}



