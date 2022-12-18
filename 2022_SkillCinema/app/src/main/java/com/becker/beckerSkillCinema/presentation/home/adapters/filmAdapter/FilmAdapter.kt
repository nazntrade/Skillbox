package com.becker.beckerSkillCinema.presentation.home.adapters.filmAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.Genre
import com.becker.beckerSkillCinema.databinding.ItemFilmBinding
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.utils.loadImage

class FilmAdapter(
    private val maxListSize: Int,
    private val clickNextButton: () -> Unit,
    private val clickFilms: (filmId: Int) -> Unit
) : ListAdapter<HomeItem, FilmAdapter.FilmViewHolder>(FilmDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmViewHolder(
        ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        if (position == maxListSize - 1) {
            holder.bindNextShow { clickNextButton() }
        } else {
            holder.bindItem(getItem(position)) { clickFilms(it) }
        }
    }

    class FilmDiffUtilCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
           return oldItem.filmId == newItem.filmId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }
    }

    class FilmViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindNextShow(clickNextButton: () -> Unit) {
            binding.apply {
                showAll.isInvisible = false
                itemFilm.isInvisible = true
            }

            binding.btnArrowShowAll.setOnClickListener { clickNextButton() }
        }

        fun bindItem(item: HomeItem, clickFilms: (filmId: Int) -> Unit) {
            binding.apply {
                showAll.isInvisible = true
                itemFilm.isInvisible = false
                itemFilmName.text = item.nameRu
                itemFilmGenre.text = createGenreName(item.genres)
                itemFilmPoster.loadImage(item.posterUrlPreview)
                if (item.rating != null) {
                    itemFilmRating.isInvisible = false
                    itemFilmRating.text = item.rating
                } else itemFilmRating.isInvisible = true
            }
            binding.itemFilm.setOnClickListener { clickFilms(item.filmId) }
        }

        private fun createGenreName(genres: List<Genre>): String {
            var genreName = ""
            genres.forEachIndexed { index, genre ->
                genreName += if (index == genres.lastIndex) genre.genre
                else "${genre.genre}, "
            }
            return genreName
        }
    }
}