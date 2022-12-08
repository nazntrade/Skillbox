package ru.zhdanon.skillcinema.ui.home.categoryrecycler

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import ru.zhdanon.skillcinema.data.CategoriesFilms
import ru.zhdanon.skillcinema.databinding.ItemCategoryListBinding
import ru.zhdanon.skillcinema.ui.CinemaViewModel
import ru.zhdanon.skillcinema.ui.home.filmrecycler.FilmAdapter

class CategoryViewHolder(
    private val binding: ItemCategoryListBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(
        maxListSize: Int,
        item: CinemaViewModel.Companion.HomeList,
        clickNextButton: (category: CategoriesFilms) -> Unit,
        clickFilms: (filmId: Int) -> Unit
    ) {
        val adapter =
            FilmAdapter(maxListSize, { clickNextButton(item.category) }, { clickFilms(it) })
        adapter.submitList(item.filmList)
        binding.titleCategory.text = item.category.text
        binding.filmList.adapter = adapter
        binding.tvTitleShowAll.apply {
            this.isInvisible = item.filmList.size < maxListSize
            this.setOnClickListener { clickNextButton(item.category) }
        }
    }
}