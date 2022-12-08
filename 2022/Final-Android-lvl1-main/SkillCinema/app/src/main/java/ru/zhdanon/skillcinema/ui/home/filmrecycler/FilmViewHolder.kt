package ru.zhdanon.skillcinema.ui.home.filmrecycler

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import ru.zhdanon.skillcinema.data.filmbyfilter.Genre
import ru.zhdanon.skillcinema.databinding.ItemFilmBinding
import ru.zhdanon.skillcinema.entity.HomeItem
import ru.zhdanon.skillcinema.app.loadImage

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