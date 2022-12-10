package com.example.screen_listpage.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.core.tools.basefrahment.BaseFragment
import com.example.core.tools.category.TypeSettings
import com.example.core.tools.general.GenreCountry
import com.example.core.tools.Order
import com.example.core.tools.SetSearch
import com.example.core.tools.Type
import com.example.core.tools.extensions.observeLoadState
import com.example.screen_listpage.R
import com.example.screen_listpage.tools.createDialogSetting
import com.example.screen_listpage.tools.createDialogYearPicker
import com.example.screen_listpage.databinding.FragmentSettingsSearchBinding
import com.example.screen_listpage.databinding.ItemSelectBinding
import kotlinx.coroutines.launch

import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsSearchFragment : BaseFragment<FragmentSettingsSearchBinding>() {
    override fun initBinding(inflater: LayoutInflater) =
        FragmentSettingsSearchBinding.inflate(inflater)

    private val viewModel by viewModel<SettingsSearchViewModel>()

    private var countryList = listOf<GenreCountry>()
    private var genreList = listOf<GenreCountry>()

    override fun onStart() {
        super.onStart()
        viewModel.getDataList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStartParam()

        setListenerSlider()

        observe()

        setOnClick()

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collect {
                countryList = it.listCountry
                genreList = it.listGenre
                setText(binding.country, "Страна", it.countryName)
                setText(binding.genres, "Жанр", it.genreName)
            }
        }
    }

    fun setOnClick() {
        binding.country.root.setOnClickListener {
            createDialogSetting(countryList, TypeSettings.COUNTRY) {
                SetSearch.counterID = it
                viewModel.getDataList()
            }
        }

        binding.genres.root.setOnClickListener {
            createDialogSetting(genreList, TypeSettings.GENRE) {
                SetSearch.genreId = it
                viewModel.getDataList()
            }
        }

        binding.years.root.setOnClickListener {
            createDialogYearPicker {
                setText(binding.years, "Год", "${SetSearch.yearFrom} - ${SetSearch.yearTo}")
            }
        }
    }


    private fun setListenerSlider() {
        binding.rangeSlider.addOnChangeListener { rangeSlider, _, _ ->
            binding.rangeFrom.text = rangeSlider.values[0].toString()
            binding.rangeTo.text = rangeSlider.values[1].toString()
            SetSearch.ratingFrom = rangeSlider.values[0].toInt()
            SetSearch.ratingTo = rangeSlider.values[1].toInt()
            if (SetSearch.ratingFrom == 0 && SetSearch.ratingTo == 10) setText(
                binding.rating, "Рейтинг", "любой"
            )
            else setText(
                binding.rating, "Рейтинг", "${SetSearch.ratingFrom} - ${SetSearch.ratingTo}"
            )
        }
    }

    private fun setStartParam() {
        when (SetSearch.order) {
            Order.YEAR -> binding.dateBestRatting.oneButton.isChecked = true
            Order.NUM_VOTE -> binding.dateBestRatting.twoButton.isChecked = true
            Order.RATING -> binding.dateBestRatting.threeButton.isChecked = true
        }
        binding.dateBestRatting.filter.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.one_button -> SetSearch.order = Order.YEAR
                R.id.two_button -> SetSearch.order = Order.NUM_VOTE
                R.id.three_button -> SetSearch.order = Order.RATING
            }
        }

        when (SetSearch.type) {
            Type.ALL -> binding.type.oneButton.isChecked = true
            Type.FILM -> binding.type.twoButton.isChecked = true
            Type.TV_SERIES -> binding.type.threeButton.isChecked = true
        }

        binding.type.filter.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.one_button -> SetSearch.type = Type.ALL
                R.id.two_button -> SetSearch.type = Type.FILM
                R.id.three_button -> SetSearch.type = Type.TV_SERIES
            }
        }

        binding.type.oneButton.text = Type.ALL.text
        binding.type.twoButton.text = Type.FILM.text
        binding.type.threeButton.text = Type.TV_SERIES.text

        binding.dateBestRatting.oneButton.text = Order.YEAR.text
        binding.dateBestRatting.twoButton.text = Order.NUM_VOTE.text
        binding.dateBestRatting.threeButton.text = Order.RATING.text

        setText(binding.rating, "Рейтинг", "${SetSearch.ratingFrom} - ${SetSearch.ratingTo}")

        setText(binding.years, "Год", "${SetSearch.yearFrom} - ${SetSearch.yearTo}")

    }

    private fun setText(textView: ItemSelectBinding, title: String, subtitle: String) {
        textView.title.text = title
        textView.subtitle.text = subtitle
    }
}