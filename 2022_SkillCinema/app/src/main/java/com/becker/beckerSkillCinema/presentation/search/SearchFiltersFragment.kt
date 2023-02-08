package com.becker.beckerSkillCinema.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.filmByFilter.FilterCountry
import com.becker.beckerSkillCinema.databinding.FragmentSearchFiltersBinding
import com.becker.beckerSkillCinema.entity.FilterCountryGenre
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.search.adapters.SearchFiltersAdapter

class SearchFiltersFragment :
    ViewBindingFragment<FragmentSearchFiltersBinding>(FragmentSearchFiltersBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private lateinit var adapter: SearchFiltersAdapter
    private val filterValuesList = mutableListOf<FilterCountryGenre>()
    private val viewModel: SearchViewModel by activityViewModels()
    private val args: SearchFiltersFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchFiltersBackBtn.setOnClickListener { findNavController().popBackStack() }
        setAdapter()
        getFilterTypeList(args.filterType)

    }

    private fun setAdapter() {
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val dividerResource =
            ResourcesCompat.getDrawable(resources, R.drawable.divider_recyclerview, null)
        divider.setDrawable(dividerResource!!)

        adapter = SearchFiltersAdapter { onItemClick(it) }
        binding.searchFiltersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchFiltersList.adapter = adapter
        binding.searchFiltersList.addItemDecoration(divider)
    }

    private fun getFilterTypeList(filterType: String) {
        val list = when (filterType) {
            KEY_COUNTRY -> {
                binding.searchFiltersCategoryTv.text = "Страны"
                binding.searchFiltersSv.hint = "Выберете страну"
                viewModel.countries.value
            }
            else -> {
                binding.searchFiltersCategoryTv.text = "Жанры"
                binding.searchFiltersSv.hint = "Выберете жанр"
                viewModel.genres.value
            }
        }
        filterValuesList.addAll(list)
        adapter.submitList(list)
    }

    private fun onItemClick(filterType: FilterCountryGenre) {
        val newFilterValue = when (filterType) {
            is FilterCountry -> {
                viewModel.getFilters().copy(countries = mapOf(filterType.id to filterType.name))
            }
            else -> {
                viewModel.getFilters().copy(genres = mapOf(filterType.id to filterType.name))
            }
        }
        viewModel.updateFilters(newFilterValue)
        findNavController().popBackStack()
    }

    companion object {
        const val KEY_COUNTRY = "country"
        const val KEY_GENRE = "genre"
    }
}