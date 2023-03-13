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
import com.becker.beckerSkillCinema.databinding.FragmentWatchedBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.profile.adapters.WatchedAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WatchedFragment :
    ViewBindingFragment<FragmentWatchedBinding>(FragmentWatchedBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private lateinit var watchedRecycler: RecyclerView

    private val watchedAdapter = WatchedAdapterIndividual(
        onWatchedItemClick = { movie -> onItemClickWatched(movie) }
    )

    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
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
    }

    private fun onItemClickWatched(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            WatchedFragmentDirections.actionWatchedFragmentToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }
}