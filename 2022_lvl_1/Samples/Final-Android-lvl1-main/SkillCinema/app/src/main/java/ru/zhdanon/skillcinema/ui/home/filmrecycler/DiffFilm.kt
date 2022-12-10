package ru.zhdanon.skillcinema.ui.home.filmrecycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.zhdanon.skillcinema.entity.HomeItem

class DiffFilm : DiffUtil.ItemCallback<HomeItem>() {
    override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem) =
        oldItem.filmId == newItem.filmId

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem) =
        oldItem == newItem
}