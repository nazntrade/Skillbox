package com.becker.beckerSkillCinema.presentation.profile.watched

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
import com.becker.beckerSkillCinema.databinding.FragmentWatchedBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.profile.ProfileMovieViewModel
import com.becker.beckerSkillCinema.presentation.profile.watched.adapter.WatchedAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WatchedFragment : ViewBindingFragment<FragmentWatchedBinding>(FragmentWatchedBinding::inflate) {

    private lateinit var backButton: AppCompatImageButton

    private lateinit var watchedRecycler: RecyclerView

    private val watchedAdapter = WatchedAdapterIndividual(
        onWatchedItemClick = { movie -> onItemClickWatched(movie) }
    )

    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = binding.backButton

        watchedRecycler = binding.watchedRecyclerView
        watchedRecycler.adapter = watchedAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.watchedList.collectLatest {
                if (it.isNotEmpty()) {
                    watchedRecycler.isVisible = true
                    watchedAdapter.submitList(it)
                } else watchedRecycler.isVisible = false
            }
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onItemClickWatched(movie: Movie){
        profileMovieViewModel.movieSelected(movie.movieId)
//        profileMovieViewModel.getImagesList(movie.movieId)
//        profileMovieViewModel.getStaffInfo(movie.movieId)
//        profileMovieViewModel.getActorsInfo(movie.movieId)
//        profileMovieViewModel.getSimilarMovies(movie.movieId)
//        profileMovieViewModel.getSeriesInfo(movie.movieId)
        profileMovieViewModel.getMovieFromDataBaseById(movie.movieId)
        findNavController().navigate(R.id.action_watchedFragment_to_fragmentFilmDetail)
    }
}