package ru.zhdanon.skillcinema.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zhdanon.skillcinema.R
import ru.zhdanon.skillcinema.data.filmbyfilter.FilterCountry
import ru.zhdanon.skillcinema.databinding.FragmentSearchFiltersBinding
import ru.zhdanon.skillcinema.entity.FilterCountryGenre
import ru.zhdanon.skillcinema.ui.SearchViewModel
import ru.zhdanon.skillcinema.ui.search.adapter.SearchFiltersAdapter

class FragmentSearchFilters : Fragment() {
    private var _binding: FragmentSearchFiltersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchFiltersAdapter
    private val filterValuesList = mutableListOf<FilterCountryGenre>()

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchFiltersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FragmentSearchFiltersArgs by navArgs()

        setAdapter()
        getFilterTypeList(args.filterType)

        binding.searchFiltersBackBtn.setOnClickListener { requireActivity().onBackPressed() }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_COUNTRY = "country"
        const val KEY_GENRE = "genre"
    }
}