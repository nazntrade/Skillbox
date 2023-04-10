package com.becker.beckerSkillCinema.presentation.filmDetail.similar

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentAllFilmsBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.home.homeAdapters.filmAdapter.FilmAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.launch

class FragmentSimilarFilms : ViewBindingFragment<FragmentAllFilmsBinding>(
    FragmentAllFilmsBinding::inflate
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

    private val viewModel: FilmDetailViewModel by activityViewModels()
    private var adapter: FilmAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allFilmsToHomeBtn.setOnClickListener { findNavController().popBackStack() }
        binding.allFilmsCategoryTv.text = resources.getString(R.string.label_film_similar)
        setAdapter()
        setFilmList()
    }

    private fun setAdapter() {
        adapter = FilmAdapter(50, {}) { onClickFilm(it) }
        binding.allFilmsList.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.allFilmsList.adapter = adapter
    }

    private fun setFilmList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFilmSimilar.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun onClickFilm(newFilmId: Int) {
        viewModel.putFilmId(newFilmId)
        val action =
            FragmentSimilarFilmsDirections.actionFragmentSimilarFilmsToFragmentFilmDetail(newFilmId)
        findNavController().navigate(action)
    }
}