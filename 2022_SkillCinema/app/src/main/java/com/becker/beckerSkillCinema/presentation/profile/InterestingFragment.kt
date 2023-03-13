package com.becker.beckerSkillCinema.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.databinding.FragmentInterestingBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.profile.adapters.InterestingAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class InterestingFragment :
    ViewBindingFragment<FragmentInterestingBinding>(FragmentInterestingBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private lateinit var interestingRecycler: RecyclerView

    private val interestingAdapter = InterestingAdapterIndividual(
        onInterestingItemClick = { movie -> onItemClickInteresting(movie) }
    )

    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
        interestingRecycler = binding.historyRecyclerView
        interestingRecycler.adapter = interestingAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.interestingList.collectLatest {
                if (it.isNotEmpty()) {
                    interestingRecycler.isVisible = true
                    interestingAdapter.submitList(it)
                } else interestingRecycler.isVisible = false
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