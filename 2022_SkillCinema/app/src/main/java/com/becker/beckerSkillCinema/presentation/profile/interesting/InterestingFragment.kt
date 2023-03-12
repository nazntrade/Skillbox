package com.becker.beckerSkillCinema.presentation.profile.interesting

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.databinding.FragmentInterestingBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.profile.ProfileMovieViewModel
import com.becker.beckerSkillCinema.presentation.profile.interesting.adapter.InterestingAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class InterestingFragment :
    ViewBindingFragment<FragmentInterestingBinding>(FragmentInterestingBinding::inflate) {

    private lateinit var backButton: AppCompatImageButton

    private lateinit var interestingRecycler: RecyclerView

    private val interestingAdapter = InterestingAdapterIndividual(
        onInterestingItemClick = { movie -> onItemClickInteresting(movie) }
    )

    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = binding.backButton

        interestingRecycler = binding.interestingRecyclerView
        interestingRecycler.adapter = interestingAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.interestingList.collectLatest {
                if (it.isNotEmpty()) {
                    interestingRecycler.isVisible = true
                    interestingAdapter.submitList(it)
                } else interestingRecycler.isVisible = false
            }
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onItemClickInteresting(movie: Movie) {////////////////////////////////////////////
        profileMovieViewModel.movieSelected(movie.movieId)
//        profileMovieViewModel.getImagesList(movie.movieId)
//        profileMovieViewModel.getStaffInfo(movie.movieId)
//        profileMovieViewModel.getActorsInfo(movie.movieId)
//        profileMovieViewModel.getSimilarMovies(movie.movieId)
//        profileMovieViewModel.getSeriesInfo(movie.movieId)
        profileMovieViewModel.getMovieFromDataBaseById(movie.movieId)
        findNavController().navigate(R.id.action_interestingFragment_to_fragmentFilmDetail)
    }
}