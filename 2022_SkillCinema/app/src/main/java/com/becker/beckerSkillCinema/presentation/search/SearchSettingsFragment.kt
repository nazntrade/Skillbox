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

        binding.searchSettingsBackBtn.setOnClickListener { findNavController().popBackStack(R.id.fragmentSearch, false) }
        setDate()
        setTextViews()
        setRatingSlider()
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
            getString(R.string.search_set_datepicker_text,
                viewModel.getFilters().yearFrom,
                viewModel.getFilters().yearTo)

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

        binding.countryField.setOnClickListener {
            filterTypeChooseClick(SearchFiltersFragment.KEY_COUNTRY)
        }
        binding.genreField.setOnClickListener {
            filterTypeChooseClick(SearchFiltersFragment.KEY_GENRE)
        }
    }

    private fun filterTypeChooseClick(filterType: String) {
        val action = SearchSettingsFragmentDirections
            .actionSearchSettingsFragmentToSearchFiltersFragment(filterType)
        findNavController().navigate(action)
    }

    private fun setRatingSlider() {
        binding.searchSettingsRangeStart.text =
            resources.getInteger(R.integer.settings_rating_slider_start).toString()
        binding.searchSettingsRangeEnd.text =
            resources.getInteger(R.integer.settings_rating_slider_end).toString()
        binding.searchSettingsRatingSlider.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                if (slider.values == listOf(1f, 10f)) {
                    binding.searchSettingsRatingTv.text = "любой"
                } else {
                    val values = slider.values.map { it.toInt() }
                    binding.searchSettingsRatingTv.text =
                        resources.getString(
                            R.string.search_settings_rating_text,
                            values[0],
                            values[1]
                        )
                    binding.searchSettingsRangeStart.text = values[0].toString()
                    binding.searchSettingsRangeEnd.text = values[1].toString()
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
                    binding.searchSettingsRatingTv.text =
                        resources.getString(
                            R.string.search_settings_rating_text,
                            values[0],
                            values[1]
                        )
                    binding.searchSettingsRangeStart.text = values[0].toString()
                    binding.searchSettingsRangeEnd.text = values[1].toString()
                    viewModel.updateFilters(
                        viewModel.getFilters().copy(
                            ratingFrom = values[0], ratingTo = values[1]
                        )
                    )
                }
            }
        })
    }
}