package com.becker.beckerSkillCinema.presentation.search.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.databinding.ItemFilmSearchBinding
import com.becker.beckerSkillCinema.entity.HomeItem
import com.becker.beckerSkillCinema.utils.loadImage

class SearchAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<HomeItem, SearchAdapter.SearchViewHolder>(SearchDiffUtil()) {

    class SearchDiffUtil : DiffUtil.ItemCallback<HomeItem>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemFilmSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        (holder.binding).apply {
            item?.let {
                holder.binding.apply {
                    itemFilmographyImage.loadImage(item.posterUrlPreview)
                    itemFilmographyName.text = item.nameRu ?: "No name"
                    itemFilmographyGenre.text = item.genres.firstOrNull()?.genre ?: ""
                    itemFilmographyRating.text = item.rating
                    itemFilmSearch.setOnClickListener {
                        onClick(item.filmId)
                    }
                    itemFilmYear.text = item.yearHomeItem
                }
            }
        }
    }

    class SearchViewHolder(val binding: ItemFilmSearchBinding) :
        RecyclerView.ViewHolder(binding.root)
}