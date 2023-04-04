package com.becker.beckerSkillCinema.presentation.filmDetail.series

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.seasons.Season
import com.becker.beckerSkillCinema.databinding.FragmentSeasonsBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.becker.beckerSkillCinema.presentation.filmDetail.series.adapter.SeasonsAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.launch

class FragmentSeasons :
    ViewBindingFragment<FragmentSeasonsBinding>(FragmentSeasonsBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val args: FragmentSeasonsArgs by navArgs()
    private val viewModel: FilmDetailViewModel by activityViewModels()
    private var adapter: SeasonsAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seriesBack.setOnClickListener { findNavController().popBackStack() }
        setAdapter()                        // set adapter
        setFirstDefaultEpisodeList()        // get episodes and then set them in ChipGroup
        binding.seriesNameTv.text = args.seriesName
    }

    private fun setAdapter() {
        adapter = SeasonsAdapter()

        binding.seriesEpisodeList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.seriesEpisodeList.adapter = adapter
    }

    private fun setFirstDefaultEpisodeList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.seasons.collect { allSeasons ->
                    binding.seasonsAndEpisodesCount.text =
                        getSeasonsLabel(
                            allSeasons[0].number,
                            allSeasons[0].episodes.size
                        )
                    adapter.submitList(allSeasons[0].episodes)
                    setChipGroup(allSeasons)
                }
            }
        }
    }

    private fun setChipGroup(allSeasons: List<Season>) {
        val chipGroup = ChipGroup(requireContext()).apply {
            isSingleSelection = true
            chipSpacingHorizontal = 8
        }

        // for every indices in allSeasons
        for (i in allSeasons.indices) {
            val chip = Chip(requireContext()).apply {
                text = resources.getString(R.string.season_chip_name, i + 1)
                chipBackgroundColor = chipBackColors
                setTextColor(chipTextColors)
                chipStrokeColor = chipStrokeColors
                chipStartPadding = 24f
                chipEndPadding = 24f
                isCheckable = true
                checkedIcon = null
                chipStrokeWidth = 1f
                isSelected = false
                isChecked = chipGroup.size == 0
            }
            chip.setOnClickListener {
                binding.seasonsAndEpisodesCount.text =
                    getSeasonsLabel(allSeasons[i].number, allSeasons[i].episodes.size)
                adapter.submitList(allSeasons[i].episodes)
            }
            chipGroup.addView(chip)
        }
        binding.seriesChipsGroupContainer.addView(chipGroup)
    }

    private fun getSeasonsLabel(seasonNumber: Int, episodeCount: Int): String {
        val episodesStr = resources.getQuantityString(
            R.plurals.film_details_episode_count,
            episodeCount,
            episodeCount
        )
        return resources.getString(
            R.string.episodes_count,
            seasonNumber,
            episodesStr
        )
    }

    companion object {
        val chipBackColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(Color.BLUE, Color.WHITE)
        )
        val chipTextColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(Color.WHITE, Color.BLACK)
        )
        val chipStrokeColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(Color.RED, Color.BLACK)
        )
    }
}