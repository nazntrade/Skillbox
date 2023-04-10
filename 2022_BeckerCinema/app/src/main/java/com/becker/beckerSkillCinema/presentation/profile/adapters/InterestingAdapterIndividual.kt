package com.becker.beckerSkillCinema.presentation.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.databinding.ItemFilmBinding
import com.bumptech.glide.Glide

class InterestingAdapterIndividual(
    val onInterestingItemClick: (Movie) -> Unit
) : ListAdapter<Movie, InterestingHolderIndividual>(DiffUtilCallBackWatched()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestingHolderIndividual {
        return InterestingHolderIndividual(
            binding = ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onInterestingItemClick = onInterestingItemClick
        )
    }

    override fun onBindViewHolder(holder: InterestingHolderIndividual, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}

class InterestingHolderIndividual(
    val binding: ItemFilmBinding, val onInterestingItemClick: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        with(binding) {
            Glide.with(itemFilmPoster.context).load(item.posterUri).centerCrop()
                .into(itemFilmPoster)
            itemFilmGenre.text = item.genre ?: ""
            itemFilmName.text = item.movieName ?: item.nameEn
            if (item.rating == null) {
                itemFilmRating.isInvisible = true
            } else {
                itemFilmRating.isVisible = true
                itemFilmRating.text = item.rating.toString()
            }
            itemFilmYear.text = item.year.toString()
        }
        binding.root.setOnClickListener {
            onInterestingItemClick(item)
        }
    }
}



