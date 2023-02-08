package com.becker.beckerSkillCinema.presentation.search.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.databinding.ItemSearchFiltersBinding
import com.becker.beckerSkillCinema.entity.FilterCountryGenre

class SearchFiltersAdapter(
    private val onItemClick: (FilterCountryGenre) -> Unit
) : ListAdapter<FilterCountryGenre, SearchFiltersAdapter.SearchFiltersViewHolder>(SearchFiltersDiffUtil()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchFiltersViewHolder(
        ItemSearchFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchFiltersViewHolder, position: Int) {
        val item = getItem(position)
        var isSelected = false
        if (item.name.isNotEmpty()) {
            holder.binding.apply {
                searchFilterName.text = item.name
                searchFilterName.setOnClickListener {
                    onItemClick(item)
                    isSelected = !isSelected
                    if (isSelected) {
                        searchFilterName.setBackgroundColor(Color.DKGRAY)
                    } else {
                        searchFilterName.setBackgroundColor(Color.WHITE)
                    }
                }
            }
        }
    }

    class SearchFiltersViewHolder(val binding: ItemSearchFiltersBinding) :
        RecyclerView.ViewHolder(binding.root)
}