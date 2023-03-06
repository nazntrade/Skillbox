package com.becker.beckerSkillCinema.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
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
import com.becker.beckerSkillCinema.presentation.search.adapters.CountryGenreSearchFiltersAdapter

class CountryGenreSearchFiltersFragment :
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

    private lateinit var searchView: SearchView
    private var listToDisplay = mutableListOf<FilterCountryGenre>()
    private lateinit var adapter: CountryGenreSearchFiltersAdapter
    private val filterValuesList = mutableListOf<FilterCountryGenre>()
    private val viewModel: SearchViewModel by activityViewModels()
    private val args: CountryGenreSearchFiltersFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchFiltersSv
        binding.searchFiltersBackBtn.setOnClickListener { findNavController().popBackStack() }
        setAdapter()
        getFilterTypeList(args.filterType)
    }

    private fun setAdapter() {
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val dividerResource =
            ResourcesCompat.getDrawable(resources, R.drawable.divider_recyclerview, null)
        divider.setDrawable(dividerResource!!)

        adapter = CountryGenreSearchFiltersAdapter { onItemClick(it) }
        binding.searchFiltersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchFiltersList.adapter = adapter
        binding.searchFiltersList.addItemDecoration(divider)
    }

    private fun getFilterTypeList(filterType: String) {
        val list = when (filterType) {
            KEY_COUNTRY -> {
                binding.searchFiltersCategoryTv.text = "Страны"
                binding.searchFiltersSv.queryHint = "Выберете страну"
                viewModel.countries.value
            }
            else -> {
                binding.searchFiltersCategoryTv.text = "Жанры"
                binding.searchFiltersSv.queryHint = "Выберете жанр"
                viewModel.genres.value
            }
        }
        filterValuesList.addAll(list)
        adapter.submitList(list)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null || query.isEmpty()) {
                    adapter.submitList(list.toMutableList())
                } else {
                    listToDisplay.clear()
                    list.forEach {
                        if (it.name.contains(
                                query,
                                true
                            )
                        ) listToDisplay.add(it)
                    }
                    if (listToDisplay.isNotEmpty()) {
                        adapter.submitList(listToDisplay.toMutableList())
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null || newText.isEmpty()) {
                    adapter.submitList(list.toMutableList())
                } else {
                    listToDisplay.clear()
                    val search = newText.lowercase()
                    list.forEach {
                        if (it.name.contains(
                                search,
                                true
                            )
                        ) listToDisplay.add(it)
                    }
                    if (listToDisplay.isNotEmpty()) {
                        adapter.submitList(listToDisplay.toMutableList())
                    }
                }
                return true
            }
        })
    }

    private fun onItemClick(filterType: FilterCountryGenre) {
        val newFilterValue =
            when (filterType) {
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