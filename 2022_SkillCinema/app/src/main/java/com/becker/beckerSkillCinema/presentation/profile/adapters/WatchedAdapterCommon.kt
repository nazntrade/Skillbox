package com.becker.beckerSkillCinema.presentation.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.databinding.ClearHistoryItemBinding
import com.becker.beckerSkillCinema.databinding.ItemFilmBinding
import com.bumptech.glide.Glide

open class WatchedAdapterCommon(
    val onWatchedItemClick: (Movie) -> Unit,
    val onClearHistoryClick: (View) -> Unit
) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtilCallBackWatched()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_MOVIES) {
            WatchedViewHolderCommon(
                binding = ItemFilmBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ),
                onWatchedItemClick = onWatchedItemClick
            )
        } else ClearHistoryViewHolder(
            binding = ClearHistoryItemBinding.inflate(
                layoutInflater,
                parent,
                false
            ),
            onClearHistoryClick = onClearHistoryClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            if (getItemViewType(position) == ITEM_MOVIES) {
                (holder as WatchedViewHolderCommon).bind(it)
            } else (holder as ClearHistoryViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 10) ITEM_CLEAR_HISTORY
        else ITEM_MOVIES
    }

    companion object {
        private const val ITEM_MOVIES = 0
        private const val ITEM_CLEAR_HISTORY = 1
    }
}

class ClearHistoryViewHolder(
    val binding: ClearHistoryItemBinding,
    val onClearHistoryClick: (View) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        with(binding) {
            trashPicture.setOnClickListener {
                onClearHistoryClick(it)
            }
        }
    }
}

class WatchedViewHolderCommon(
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
            if (item.rating == null) {
                itemFilmRating.isInvisible = true
            } else {
                itemFilmRating.isVisible = true
                itemFilmRating.text = item.rating.toString()
            }
            itemFilmYear.text = item.year.toString()
        }
        binding.root.setOnClickListener {
            onWatchedItemClick(item)
        }
    }
}

class DiffUtilCallBackWatched : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}