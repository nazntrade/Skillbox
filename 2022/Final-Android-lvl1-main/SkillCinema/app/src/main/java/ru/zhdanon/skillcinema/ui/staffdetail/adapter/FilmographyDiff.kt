package ru.zhdanon.skillcinema.ui.staffdetail.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.zhdanon.skillcinema.data.staffbyid.StaffsFilms

class FilmographyDiff : DiffUtil.ItemCallback<StaffsFilms>() {
    override fun areItemsTheSame(oldItem: StaffsFilms, newItem: StaffsFilms) =
        oldItem.filmId == newItem.filmId

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: StaffsFilms,
        newItem: StaffsFilms
    ) = oldItem == newItem
}