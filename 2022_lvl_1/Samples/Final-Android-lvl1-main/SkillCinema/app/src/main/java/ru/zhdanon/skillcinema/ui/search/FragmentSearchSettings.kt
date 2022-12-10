package ru.zhdanon.skillcinema.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.RangeSlider
import ru.zhdanon.skillcinema.R
import ru.zhdanon.skillcinema.databinding.FragmentSearchSettingsBinding
import ru.zhdanon.skillcinema.ui.SearchViewModel

class FragmentSearchSettings : Fragment() {
    private var _binding: FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchSettingsBackBtn.setOnClickListener { requireActivity().onBackPressed() }

        setTextViews()                  // Установка значений в TextView
        setRatingSlider()               // Установка Slider рейтинга
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

        binding.searchSettingsCountryTv.setOnClickListener {
            filterTypeChooseClick(FragmentSearchFilters.KEY_COUNTRY)
        }
        binding.searchSettingsGenreTv.setOnClickListener {
            filterTypeChooseClick(FragmentSearchFilters.KEY_GENRE)
        }
    }

    private fun filterTypeChooseClick(filterType: String) {
        val action = FragmentSearchSettingsDirections
            .actionFragmentSearchSettingsToFragmentSearchFilters(filterType)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}