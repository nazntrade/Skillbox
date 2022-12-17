package com.becker.beckerSkillCinema.presentation.home.adapters.categoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.databinding.ItemCategoryListBinding
import com.becker.beckerSkillCinema.presentation.home.HomeViewModel
import com.becker.beckerSkillCinema.presentation.home.adapters.filmAdapter.FilmAdapter

class CategoryAdapter(
    private val maxListSize: Int,
    private val category: List<HomeViewModel.Companion.HomeList>,
    private val clickNextButton: (category: CategoriesFilms) -> Unit,
    private val clickFilms: (filmId: Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindItem(
            maxListSize,
            category[position],
            { clickNextButton(it) },
            { clickFilms(it) }
        )
    }

    override fun getItemCount() = category.size

    class CategoryViewHolder(
        private val binding: ItemCategoryListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(
            maxListSize: Int,
            item: HomeViewModel.Companion.HomeList,
            clickNextButton: (category: CategoriesFilms) -> Unit,
            clickFilms: (filmId: Int) -> Unit
        ) {
            val adapter =
                FilmAdapter(maxListSize, { clickNextButton(item.category) }, { clickFilms(it) })
            adapter.submitList(item.filmList.shuffled())

            binding.titleCategory.text = item.category.text
            binding.filmList.adapter = adapter
            binding.tvTitleShowAll.apply {
                this.isInvisible = item.filmList.size < maxListSize
                this.setOnClickListener { clickNextButton(item.category) }
            }
        }
    }
}