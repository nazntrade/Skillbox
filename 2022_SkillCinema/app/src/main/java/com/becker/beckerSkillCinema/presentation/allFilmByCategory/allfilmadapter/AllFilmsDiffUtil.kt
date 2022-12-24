package com.becker.beckerSkillCinema.presentation.allFilmByCategory.allfilmadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.becker.beckerSkillCinema.entity.HomeItem

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