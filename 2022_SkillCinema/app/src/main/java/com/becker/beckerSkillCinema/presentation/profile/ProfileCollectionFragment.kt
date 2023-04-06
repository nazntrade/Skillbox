package com.becker.beckerSkillCinema.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.data.profile.Collections
import com.becker.beckerSkillCinema.databinding.FragmentCollectionProfileBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.profile.adapters.InterestingAdapterIndividual
import com.becker.beckerSkillCinema.utils.MyStrings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileCollectionFragment :
    ViewBindingFragment<FragmentCollectionProfileBinding>(FragmentCollectionProfileBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private lateinit var collectionTitle: AppCompatTextView
    private lateinit var collectionRecycler: RecyclerView
    private val collectionAdapter = InterestingAdapterIndividual(
        onInterestingItemClick = { movie -> onItemClick(movie) }
    )
    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { findNavController().popBackStack() }
        collectionRecycler = binding.collectionRecyclerView
        collectionRecycler.adapter = collectionAdapter
        collectionTitle = binding.collectionTitle
        getMovies()
        chooseWhichListDownloadAndDisplay()
    }

    private fun getMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllToWatch().collectLatest { list ->
                    profileMovieViewModel.buildToWatchList(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllFavorites().collectLatest { list ->
                    profileMovieViewModel.buildFavoritesList(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                    profileMovieViewModel.buildCustomCollectionList(list)
                }
            }
        }
    }

    private fun chooseWhichListDownloadAndDisplay() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.chosenCollection.collectLatest { collections ->
                    when (collections) {
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
        }
    }

    private fun onItemClick(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            ProfileCollectionFragmentDirections
                .actionProfileCollectionFragmentToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }

    companion object {
        private val FAVORITES = MyStrings.get(R.string.favorites)
        private val TO_WATCH = MyStrings.get(R.string.want_to_watch)
    }
}