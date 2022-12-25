package com.becker.beckerSkillCinema.presentation.allFilmByCategory.allfilmadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.becker.beckerSkillCinema.databinding.ItemFilmBinding
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.utils.loadImage

class AllFilmAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<HomeItem, AllFilmViewHolder>(AllFilmsDiffUtil()) {
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
}