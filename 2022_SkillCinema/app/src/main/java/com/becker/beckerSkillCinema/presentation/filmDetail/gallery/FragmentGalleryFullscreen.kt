package com.becker.beckerSkillCinema.presentation.filmDetail.gallery

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.becker.beckerSkillCinema.databinding.FragmentGalleryDetailFullscreenBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.filmDetail.gallery.pagerAdapter.GalleryFullscreenAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.launch

class FragmentGalleryFullscreen : ViewBindingFragment<FragmentGalleryDetailFullscreenBinding>(
    FragmentGalleryDetailFullscreenBinding::inflate
) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private val args: FragmentGalleryFullscreenArgs by navArgs()

    private val viewModel: FilmDetailViewModel by activityViewModels()
    private var adapter: GalleryFullscreenAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GalleryFullscreenAdapter()

        PagerSnapHelper().attachToRecyclerView(binding.galleryImageFullscreenContainer)

        binding.galleryImageFullscreenContainer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.galleryImageFullscreenContainer.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.galleryByType.collect {
                adapter.submitData(it)
            }
        }
        binding.galleryImageFullscreenContainer.scrollToPosition(args.position)
    }
}