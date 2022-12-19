package com.becker.beckerSkillCinema.presentation.home.adapters.categoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.data.HomeList
import com.becker.beckerSkillCinema.databinding.ItemCategoryListBinding
import com.becker.beckerSkillCinema.presentation.home.adapters.filmAdapter.FilmAdapter

class CategoryAdapter(
    private val maxListSize: Int,
    private val homeLists: List<HomeList>,
    private val clickNextButton: (category: CategoriesFilms) -> Unit,
    private val clickFilms: (filmId: Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindItem(
            maxListSize,
            homeLists[position],
            { clickNextButton(it) },
            { clickFilms(it) }
        )
    }

    override fun getItemCount() = homeLists.size

    class CategoryViewHolder(
        private val binding: ItemCategoryListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(
            maxListSize: Int,
            itemHomeList: HomeList,
            clickNextButton: (category: CategoriesFilms) -> Unit,
            clickFilms: (filmId: Int) -> Unit
        ) {

            //https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter
            val filmAdapter =
                FilmAdapter(
                    maxListSize,
                    { clickNextButton(itemHomeList.category) },
                    { clickFilms(it) }
                )
            filmAdapter.submitList(itemHomeList.filmList.shuffled()) // '.submitList' - standard option for ListAdapter

            binding.titleCategory.text = itemHomeList.category.text
            binding.filmList.adapter = filmAdapter
            binding.tvTitleShowAll.apply {
                if (itemHomeList.filmList.size < maxListSize) {
                    this.isInvisible = true
                }
                this.setOnClickListener { clickNextButton(itemHomeList.category) }
            }
        }
    }
}