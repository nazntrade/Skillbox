package com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffDetail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.becker.beckerSkillCinema.data.PROFESSIONS
import com.becker.beckerSkillCinema.data.staffById.StaffsFilms
import com.becker.beckerSkillCinema.databinding.FragmentStaffFilmographyBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffDetail.adapter.FilmographyAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FragmentFilmography :
    ViewBindingFragment<FragmentStaffFilmographyBinding>(FragmentStaffFilmographyBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val viewModel: StaffDetailViewModel by activityViewModels()
    private lateinit var adapter: FilmographyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()                // set adapter
        setFilmList()               // get movieList
    }

    private fun setAdapter() {
        adapter = FilmographyAdapter { onFilmClick(it) }
        binding.filmographyList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filmographyList.adapter = adapter
    }

    private fun setFilmList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentStaff.collect { staff ->
                if (staff != null) {
                    if (staff.films?.isNotEmpty() == true) {
                        setChipButton(staff.films)
                        adapter.submitList(staff.films)
                    }
                }
            }
        }
    }

    private fun onFilmClick(filmId: Int) {
        val action =
            FragmentFilmographyDirections.actionFragmentFilmographyToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    private fun setChipButton(filmList: List<StaffsFilms>) {
        val chipGroup = ChipGroup(requireContext()).apply {
            isSingleSelection = true
            chipSpacingHorizontal = 8
        }
        val professionList = getProfessionList(filmList)
        professionList.forEach { profession ->
            val chip = Chip(requireContext()).apply {
                text = PROFESSIONS.getValue(profession)
                transitionName = profession
                chipBackgroundColor = chipBackColors
                setTextColor(chipTextColors)
                chipStrokeColor = chipStrokeColors
                isCheckable = true
                checkedIcon = null
                chipStrokeWidth = 1f
                isSelected = false
                isChecked = chipGroup.size == 0
            }
            chip.setOnClickListener { myChip ->
                val newFilmList = filmList.filter { film ->
                    film.professionKey == myChip.transitionName
                }
                adapter.submitList(newFilmList)
            }
            chipGroup.addView(chip)
        }
        binding.seriesChipsGroupContainer.addView(chipGroup)
    }

    private fun getProfessionList(filmList: List<StaffsFilms>): List<String> {
        val tempList = mutableSetOf<String>()
        filmList.forEach { film ->
            if (PROFESSIONS.containsKey(film.professionKey)) tempList.add(film.professionKey)
        }
        return tempList.toList()
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
            intArrayOf(Color.BLUE, Color.BLACK)
        )
    }
}