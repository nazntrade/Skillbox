package com.becker.beckerSkillCinema.presentation.profile.customCollection

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.data.profile.Collections
import com.becker.beckerSkillCinema.databinding.FragmentCollectionProfileBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.profile.ProfileMovieViewModel
import com.becker.beckerSkillCinema.presentation.profile.interesting.adapter.InterestingAdapterIndividual
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileCollectionFragment :
    ViewBindingFragment<FragmentCollectionProfileBinding>(FragmentCollectionProfileBinding::inflate) {

    private lateinit var backButton: AppCompatImageButton
    private lateinit var collectionTitle: AppCompatTextView

    private lateinit var collectionRecycler: RecyclerView

    private val collectionAdapter = InterestingAdapterIndividual(
        onInterestingItemClick = { movie -> onItemClick(movie) }
    )

    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = binding.backButton

        collectionRecycler = binding.collectionRecyclerView
        collectionRecycler.adapter = collectionAdapter

        collectionTitle = binding.collectionTitle

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllToWatch().collectLatest { list ->
                profileMovieViewModel.buildToWatchList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllFavorites().collectLatest { list ->
                profileMovieViewModel.buildFavoritesList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                profileMovieViewModel.buildCustomCollectionList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.collectionChosen.collectLatest {
                when (it) {
                    Collections.Favorites -> {
                        collectionTitle.text = FAVORITES
                        profileMovieViewModel.favoritesList.collectLatest {
                            if (it.isNotEmpty()) {
                                collectionRecycler.isVisible = true
                                collectionAdapter.submitList(it)
                            } else collectionRecycler.isVisible = false
                        }
                    }
                    Collections.Custom -> {
                        collectionTitle.text =
                            profileMovieViewModel.customCollectionChosen.value?.collectionName
                        profileMovieViewModel.customCollectionList.collectLatest {
                            if (it.isNotEmpty()) {
                                collectionRecycler.isVisible = true
                                collectionAdapter.submitList(it)
                            } else collectionRecycler.isVisible = false
                        }
                    }
                    Collections.ToWatch -> {
                        collectionTitle.text = TO_WATCH
                        profileMovieViewModel.toWatchList.collectLatest {
                            if (it.isNotEmpty()) {
                                collectionRecycler.isVisible = true
                                collectionAdapter.submitList(it)
                            } else collectionRecycler.isVisible = false
                        }
                    }
                }
            }
        }

        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onItemClick(movie: Movie) {
        profileMovieViewModel.movieSelected(movie.movieId)
//        profileMovieViewModel.getImagesList(movie.movieId)
//        profileMovieViewModel.getStaffInfo(movie.movieId)
//        profileMovieViewModel.getActorsInfo(movie.movieId)
//        profileMovieViewModel.getSimilarMovies(movie.movieId)
//        profileMovieViewModel.getSeriesInfo(movie.movieId)
        profileMovieViewModel.getMovieFromDataBaseById(movie.movieId)
        findNavController().navigate(R.id.action_profileCollectionFragment_to_fragmentFilmDetail)
    }

    companion object {
        private const val FAVORITES = "Любимые фильмы"
        private const val TO_WATCH = "Хочу посмотреть"
    }
}