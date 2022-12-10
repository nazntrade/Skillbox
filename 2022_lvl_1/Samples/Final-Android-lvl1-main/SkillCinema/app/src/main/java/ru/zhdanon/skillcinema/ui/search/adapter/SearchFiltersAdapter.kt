package ru.zhdanon.skillcinema.ui.search.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.zhdanon.skillcinema.databinding.ItemSearchFiltersBinding
import ru.zhdanon.skillcinema.entity.FilterCountryGenre

class SearchFiltersAdapter(
    private val onItemClick: (FilterCountryGenre) -> Unit
) : ListAdapter<FilterCountryGenre, SearchFiltersViewHolder>(SearchFiltersDiffUtil()) {

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
}