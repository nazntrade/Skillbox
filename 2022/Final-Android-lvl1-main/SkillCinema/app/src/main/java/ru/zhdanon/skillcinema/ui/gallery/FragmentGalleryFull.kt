package ru.zhdanon.skillcinema.ui.gallery

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.zhdanon.skillcinema.R
import ru.zhdanon.skillcinema.data.GALLERY_TYPES
import ru.zhdanon.skillcinema.databinding.FragmentFilmGalleryBinding
import ru.zhdanon.skillcinema.ui.CinemaViewModel
import ru.zhdanon.skillcinema.ui.gallery.recycleradapter.GalleryFullAdapter

class FragmentGalleryFull : Fragment() {
    private var _binding: FragmentFilmGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var galleryAdapter: GalleryFullAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setChipButton()             // Установка Chip-group и получение изображений

        binding.galleryBackBtn.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setChipButton() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.galleryChipList.collect {
                val chipGroup = ChipGroup(requireContext()).apply {
                    isSingleSelection = true
                    chipSpacingHorizontal = 8
                }
                it.forEach { (key, value) ->
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
                            chipStrokeWidth = 1f
                            isSelected = false
                        }
                        chip.setOnClickListener { myChip ->
                            viewModel.updateParamsFilterGallery(galleryType = myChip.transitionName)
                            galleryAdapter.refresh()
                        }
                        if (chipGroup.size == 0) {
                            chip.isChecked = true
                            setGalleryImages(chip.transitionName)
                            galleryAdapter.refresh()
                        }
                        chipGroup.addView(chip)
                    }
                }
                binding.galleryChipsGroupContainer.addView(chipGroup)
            }
        }
    }

    // Получение изображений
    private fun setGalleryImages(galleryType: String) {
        galleryAdapter = GalleryFullAdapter { onClick(it) }

        val gridManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                .apply {
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
        val action = FragmentGalleryFullDirections
            .actionFragmentGalleryToFragmentGalleryFullscreen(position)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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