package com.becker.beckerSkillCinema.presentation.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.persomFronSearch.PeopleFromSearch
import com.becker.beckerSkillCinema.databinding.ItemFilmSearchBinding
import com.becker.beckerSkillCinema.utils.loadImage

class SearchPeopleAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<PeopleFromSearch, SearchPeopleAdapter.StaffViewHolder>(DiffStaff()) {

    class DiffStaff : DiffUtil.ItemCallback<PeopleFromSearch>() {
        override fun areItemsTheSame(
            oldItem: PeopleFromSearch,
            newItem: PeopleFromSearch
        ) = oldItem.kinopoiskId == newItem.kinopoiskId

        override fun areContentsTheSame(
            oldItem: PeopleFromSearch,
            newItem: PeopleFromSearch
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StaffViewHolder(
        ItemFilmSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val item = getItem(position)
        (holder.binding).apply {
            item?.let {
                itemFilmographyImage.loadImage(item.posterUrl)
                itemFilmographyName.text = item.nameRu ?: item.nameEn ?: "Не указан"
                itemFilmographyRating.isVisible = false
                itemFilmSearch.setOnClickListener {
                    onClick(item.kinopoiskId)
                }
            }
        }
    }

    class StaffViewHolder(val binding: ItemFilmSearchBinding) :
        RecyclerView.ViewHolder(binding.root)
}