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
import com.becker.beckerSkillCinema.databinding.FragmentWatchedBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.profile.adapters.WatchedAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchedFragment : ViewBindingFragment<FragmentWatchedBinding>(
    FragmentWatchedBinding::inflate
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

    private val watchedAdapter = WatchedAdapterIndividual(
        onWatchedItemClick = { movie -> onItemClickWatched(movie) }
    )
    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backBtn.setOnClickListener { findNavController().popBackStack() }
            searchFiltersCategoryTv.text = getText(R.string.marked_watched)
            watchedRecyclerView.adapter = watchedAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.watchedList.collectLatest {
                    if (it.isNotEmpty()) {
                        binding.watchedRecyclerView.isVisible = true
                        watchedAdapter.submitList(it)
                    } else binding.watchedRecyclerView.isVisible = false
                }
            }
        }
    }

    private fun onItemClickWatched(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            WatchedFragmentDirections.actionWatchedFragmentToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }
}