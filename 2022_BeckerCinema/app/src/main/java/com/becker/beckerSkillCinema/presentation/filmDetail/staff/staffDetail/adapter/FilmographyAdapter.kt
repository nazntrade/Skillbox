package com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.staffById.StaffsFilms
import com.becker.beckerSkillCinema.databinding.ItemFilmographyFilmBinding
import com.becker.beckerSkillCinema.utils.ConstantsAndParams.PROFESSIONS
import com.becker.beckerSkillCinema.utils.loadImage

class FilmographyAdapter(
    private val onFilmClick: (Int) -> Unit
) : ListAdapter<StaffsFilms, FilmographyAdapter.FilmographyViewHolder>(FilmographyDiff()) {

    class FilmographyDiff : DiffUtil.ItemCallback<StaffsFilms>() {
        override fun areItemsTheSame(oldItem: StaffsFilms, newItem: StaffsFilms) =
            oldItem.filmId == newItem.filmId

        override fun areContentsTheSame(
            oldItem: StaffsFilms,
            newItem: StaffsFilms
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmographyViewHolder(
        ItemFilmographyFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val item = getItem(position)
        val poster = "https://kinopoiskapiunofficial.tech/images/posters/kp/${item.filmId}.jpg"
        holder.binding.apply {
            itemFilmographyImage.loadImage(poster)
            itemFilmographyName.text = item.nameRu ?: item.nameEn
            itemFilmographyGenre.text = PROFESSIONS[item.professionKey]
        }
        holder.binding.root.setOnClickListener { onFilmClick(item.filmId) }
    }

    class FilmographyViewHolder(val binding: ItemFilmographyFilmBinding) :
        RecyclerView.ViewHolder(binding.root)
}