package com.becker.beckerSkillCinema.presentation.filmDetail.gallery

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.GALLERY_TYPES
import com.becker.beckerSkillCinema.databinding.FragmentFilmGalleryScreenBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.filmDetail.gallery.recyclerAdapter.GalleryFullAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FragmentGalleryFull :
    ViewBindingFragment<FragmentFilmGalleryScreenBinding>(FragmentFilmGalleryScreenBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val viewModel: FilmDetailViewModel by activityViewModels()
    private var galleryAdapter: GalleryFullAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setChipButtons()     // Set Chip-group and get images
    }

    private fun setChipButtons() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.numberOfPicturesByCategory.collect { categoriesWithNumbers ->
                // creating chipGroup
                val chipGroup = ChipGroup(requireContext()).apply {
                    isSingleSelection = true
                    chipSpacingHorizontal = 8
                }
                // creating chip
                categoriesWithNumbers.forEach { (key, value) ->
                    if (value != 0) {
                        val nameChip = GALLERY_TYPES[key]
                        val chip = Chip(requireContext()).apply {
                            text = resources.getString(R.string.chip_name, nameChip, value)
                            chipBackgroundColor = chipBackColors
                            setTextColor(chipTextColors)
                            chipStrokeColor = chipStrokeColors
                            isCheckable = true
                            checkedIcon = null
                            transitionName = key
                            chipStrokeWidth = 1F
                            isSelected = false
                        }
                        chip.setOnClickListener { myChipClicked ->
                            viewModel.updateParamsFilterGallery(
                                galleryType = myChipClicked.transitionName
                            )
                            galleryAdapter.refresh()
                        }
                        if (chipGroup.size == 0) {
                            chip.isChecked = true
                            setGalleryImages(chip.transitionName)
                            galleryAdapter.refresh()
                        }
                        // adding every chip to chipGroup
                        chipGroup.addView(chip)
                    }
                }
                // placing chipGroup in view
                binding.galleryChipsGroupContainer.addView(chipGroup)
            }
        }
    }

    // get images
    private fun setGalleryImages(galleryType: String) {
        galleryAdapter = GalleryFullAdapter { onClick(it) }

        val gridManager =
            GridLayoutManager(
                context,
                2,
                GridLayoutManager.VERTICAL,
                false
            ).apply {
                spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 0) 2 else 1
                    }
                }
            }

        binding.filmGalleryPager.layoutManager = gridManager
        binding.filmGalleryPager.adapter = galleryAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.updateParamsFilterGallery(galleryType = galleryType)
            viewModel.galleryByType.collect {
                galleryAdapter.submitData(it)
            }
        }
    }

    private fun onClick(position: Int) {
        val action =
            FragmentGalleryFullDirections.actionFragmentGalleryToFragmentGalleryFullscreen(position)
        findNavController().navigate(action)
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