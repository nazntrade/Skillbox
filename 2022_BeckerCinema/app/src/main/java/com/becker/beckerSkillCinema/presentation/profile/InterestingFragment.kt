package com.becker.beckerSkillCinema.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.databinding.FragmentInterestingBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.profile.adapters.InterestingAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InterestingFragment : ViewBindingFragment<FragmentInterestingBinding>(
    FragmentInterestingBinding::inflate
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

    private val historyAdapter = InterestingAdapterIndividual(
        onInterestingItemClick = { movie -> onItemClickInteresting(movie) }
    )
    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backBtn.setOnClickListener { findNavController().popBackStack() }
            searchFiltersCategoryTv.text = getText(R.string.interesting_movies)
            historyRecyclerView.adapter = historyAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.interestingList.collectLatest {
                    if (it.isNotEmpty()) {
                        binding.historyRecyclerView.isVisible = true
                        historyAdapter.submitList(it)
                    } else binding.historyRecyclerView.isVisible = false
                }
            }
        }
    }

    private fun onItemClickInteresting(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            InterestingFragmentDirections.actionInterestingFragmentToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }
}