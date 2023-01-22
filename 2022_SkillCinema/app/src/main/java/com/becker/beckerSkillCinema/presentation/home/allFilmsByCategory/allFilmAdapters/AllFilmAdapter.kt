package com.becker.beckerSkillCinema.presentation.home.allFilmsByCategory.allFilmAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.databinding.ItemFilmBinding
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.utils.loadImage

class AllFilmAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<HomeItem, AllFilmAdapter.AllFilmViewHolder>(AllFilmsDiffUtil()) {

    class AllFilmsDiffUtil : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(
            oldItem: HomeItem,
            newItem: HomeItem
        ) = oldItem.filmId == newItem.filmId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: HomeItem,
            newItem: HomeItem
        ) = oldItem == newItem
    }

    override fun onBindViewHolder(holder: AllFilmViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                holder.binding.apply {
                    itemFilmPoster.loadImage(item.posterUrlPreview)
                    itemFilmName.text = item.nameRu
                    itemFilmGenre.text = item.genres.joinToString(", ") { it.genre }
                    itemFilmRating.text = item.rating
                }
            }
        }
        holder.binding.itemFilmPoster.setOnClickListener { onClick(item!!.filmId) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFilmViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllFilmViewHolder(binding)
    }

    class AllFilmViewHolder(
        val binding: ItemFilmBinding
    ) : RecyclerView.ViewHolder(binding.root)
}