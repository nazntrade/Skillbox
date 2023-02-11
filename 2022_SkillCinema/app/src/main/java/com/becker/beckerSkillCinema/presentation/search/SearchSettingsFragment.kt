package com.becker.beckerSkillCinema.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentSearchSettingsBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.google.android.material.slider.RangeSlider
import java.util.*

class SearchSettingsFragment :
    ViewBindingFragment<FragmentSearchSettingsBinding>(FragmentSearchSettingsBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack(R.id.fragmentSearch, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val viewModel: SearchViewModel by activityViewModels()
    private val args: SearchSettingsFragmentArgs by navArgs()
    private val dateFrom by lazy { args.yearFrom }
    private val dateTo by lazy { args.yearTo }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchSettingsBackBtn.setOnClickListener {
                findNavController().popBackStack(
                    R.id.fragmentSearch,
                    false
                )
            }
            searchSettingsApplyBtn.setOnClickListener {
                findNavController().popBackStack(
                    R.id.fragmentSearch,
                    false
                )
            }
        }

        setDate()
        setTextViews()
        setSortOrder()
        setRatingSlider()
        setCheckBoxViewed()
        resetSettings()
    }

    private fun setCheckBoxViewed() {
        binding.checkboxIsWatched.setOnClickListener{
            if(binding.checkboxIsWatched.isChecked)binding.checkboxIsWatched.text = "Просмотрен"
            else binding.checkboxIsWatched.text = "Не просмотрен"
        }
    }

    private fun resetSettings() {
        binding.resetSettingsBtn.setOnClickListener {
            viewModel.updateFilters(
                viewModel.getFilters().copy(
                    countries = emptyMap(),
                    genres = emptyMap(),
                    order = "",
                    type = "",
                    ratingFrom = 1,
                    ratingTo = 10,
                    yearFrom = 1850,
                    yearTo = Calendar.getInstance().get(Calendar.YEAR),
                    imdbId = null,
                    keyword = ""
                )
            )
            FragmentSearchDatePicker.from = 1850
            FragmentSearchDatePicker.to = Calendar.getInstance().get(Calendar.YEAR)
            findNavController().navigate(R.id.searchSettingsFragment)
        }
    }

    private fun setDate() {
        binding.searchYear.setOnClickListener {
            val action =
                SearchSettingsFragmentDirections.actionSearchSettingsFragmentToFragmentSearchDatePicker()
            findNavController().navigate(action)
        }

        if (dateFrom != null || dateTo != null) {
            viewModel.updateFilters(
                viewModel.getFilters().copy(
                    yearFrom = dateFrom!!.toInt(),
                    yearTo = dateTo!!.toInt()
                )
            )
        }
        binding.searchSettingsYearTv.text =
            getString(
                R.string.search_set_datepicker_text,
                viewModel.getFilters().yearFrom,
                viewModel.getFilters().yearTo
            )
    }

    private fun setTextViews() {
        binding.apply {
            searchSettingsCountryTv.text =
                if (viewModel.getFilters().countries.isNotEmpty())
                    viewModel.getFilters().countries.values.first().ifEmpty {
                        getString(R.string.search_filters_countries_default)
                    }
                else getString(R.string.search_filters_countries_default)
            searchSettingsGenreTv.text =
                if (viewModel.getFilters().genres.isNotEmpty())
                    viewModel.getFilters().genres.values.first().ifEmpty {
                        getString(R.string.search_filters_genres_default)
                    }
                else getString(R.string.search_filters_genres_default)
        }

        binding.apply {
            countryField.setOnClickListener {
                filterTypeChooseClick(SearchFiltersFragment.KEY_COUNTRY)
            }
            genreField.setOnClickListener {
                filterTypeChooseClick(SearchFiltersFragment.KEY_GENRE)
            }
        }
    }

    private fun filterTypeChooseClick(filterType: String) {
        val action = SearchSettingsFragmentDirections
            .actionSearchSettingsFragmentToSearchFiltersFragment(filterType)
        findNavController().navigate(action)
    }

    private fun setSortOrder() {
        when (viewModel.getFilters().type) {
            Type.ALL.text -> binding.searchRadioAll.isChecked = true
            Type.FILM.text -> binding.searchRadioFilms.isChecked = true
            Type.TV_SERIES.text -> binding.searchRadioSeries.isChecked = true
        }

        binding.searchRadioGroupFilmType.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.search_radio_all -> viewModel.updateFilters(
                    viewModel.getFilters().copy(
                        type = Type.ALL.text
                    )
                )
                R.id.search_radio_films -> viewModel.updateFilters(
                    viewModel.getFilters().copy(
                        type = Type.FILM.text
                    )
                )
                R.id.search_radio_series -> viewModel.updateFilters(
                    viewModel.getFilters().copy(
                        type = Type.TV_SERIES.text
                    )
                )
            }
        }

        when (viewModel.getFilters().order) {
            Order.YEAR.text -> binding.searchRadioSortingDate.isChecked = true
            Order.NUM_VOTE.text -> binding.searchRadioSortingPopular.isChecked = true
            Order.RATING.text -> binding.searchRadioSortingRating.isChecked = true
        }
        binding.searchRadioGroupSorting.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.search_radio_sorting_date -> viewModel.updateFilters(
                    viewModel.getFilters().copy(
                        order = Order.YEAR.text
                    )
                )
                R.id.search_radio_sorting_popular -> viewModel.updateFilters(
                    viewModel.getFilters().copy(
                        order = Order.NUM_VOTE.text
                    )
                )
                R.id.search_radio_sorting_rating -> viewModel.updateFilters(
                    viewModel.getFilters().copy(
                        order = Order.RATING.text
                    )
                )
            }
        }
    }

    private fun setRatingSlider() {
        binding.apply {
            searchSettingsRangeStart.text = viewModel.getFilters().ratingFrom.toString()
//                resources.getInteger(R.integer.settings_rating_slider_start).toString()
            searchSettingsRangeEnd.text = viewModel.getFilters().ratingTo.toString()
//                resources.getInteger(R.integer.settings_rating_slider_end).toString()
            searchSettingsRatingSlider.setValues(
                viewModel.getFilters().ratingFrom.toFloat(),
                viewModel.getFilters().ratingTo.toFloat()
            )
        }

        binding.searchSettingsRatingSlider.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                if (slider.values == listOf(1f, 10f)) {
                    binding.searchSettingsRatingTv.text = "любой"
                } else {
                    val values = slider.values.map { it.toInt() }
                    binding.apply {
                        searchSettingsRatingTv.text =
                            resources.getString(
                                R.string.search_settings_rating_text,
                                values[0],
                                values[1]
                            )
                        searchSettingsRangeStart.text = values[0].toString()
                        searchSettingsRangeEnd.text = values[1].toString()
                    }
                    viewModel.updateFilters(
                        viewModel.getFilters().copy(
                            ratingFrom = values[0], ratingTo = values[1]
                        )
                    )
                }
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                if (slider.values == listOf(1f, 10f)) {
                    binding.searchSettingsRatingTv.text = "любой"
                } else {
                    val values = slider.values.map { it.toInt() }
                    binding.apply {
                        searchSettingsRatingTv.text =
                            resources.getString(
                                R.string.search_settings_rating_text,
                                values[0],
                                values[1]
                            )
                        searchSettingsRangeStart.text = values[0].toString()
                        searchSettingsRangeEnd.text = values[1].toString()
                    }
                    viewModel.updateFilters(
                        viewModel.getFilters().copy(
                            ratingFrom = values[0], ratingTo = values[1]
                        )
                    )
                }
            }
        })
    }

    companion object {
        enum class Order(val text: String) {
            RATING("RATING"),
            NUM_VOTE("NUM_VOTE"),
            YEAR("YEAR")
        }

        enum class Type(val text: String) {
            TV_SERIES("TV_SERIES"),
            FILM("FILM"),
            ALL("ALL"),
        }
    }
}