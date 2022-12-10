package ru.zhdanon.skillcinema.ui.search.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.zhdanon.skillcinema.entity.FilterCountryGenre

class SearchFiltersDiffUtil : DiffUtil.ItemCallback<FilterCountryGenre>() {
    override fun areItemsTheSame(
        oldItem: FilterCountryGenre,
        newItem: FilterCountryGenre
    ) = oldItem.name == newItem.name

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: FilterCountryGenre,
        newItem: FilterCountryGenre
    ) = oldItem == newItem
}