package ru.zhdanon.skillcinema.ui.staffdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.zhdanon.skillcinema.app.loadImage
import ru.zhdanon.skillcinema.data.PROFESSIONS
import ru.zhdanon.skillcinema.data.staffbyid.StaffsFilms
import ru.zhdanon.skillcinema.databinding.ItemFilmographyFilmBinding

class FilmographyAdapter(
    private val onFilmClick: (Int) -> Unit
) : ListAdapter<StaffsFilms, FilmographyViewHolder>(FilmographyDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmographyViewHolder(
        ItemFilmographyFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            itemFilmographyImage.loadImage(item.posterUrlPreview)
            itemFilmographyName.text = item.nameRu ?: item.nameEn
            itemFilmographyGenre.text = PROFESSIONS[item.professionKey]
        }
        holder.binding.root.setOnClickListener { onFilmClick(item.filmId) }
    }
}