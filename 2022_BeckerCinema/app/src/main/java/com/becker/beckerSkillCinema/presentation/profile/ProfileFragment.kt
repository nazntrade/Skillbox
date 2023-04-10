package com.becker.beckerSkillCinema.presentation.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.localData.entities.CustomCollection
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.data.profile.Collections
import com.becker.beckerSkillCinema.databinding.FragmentProfileBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.profile.adapters.CustomCollectionAdapter
import com.becker.beckerSkillCinema.presentation.profile.adapters.HistoryAdapter
import com.becker.beckerSkillCinema.presentation.profile.adapters.WatchedAdapterCommon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class ProfileFragment : ViewBindingFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val watchedAdapter = WatchedAdapterCommon(
        onWatchedItemClick = { movie -> onWatchedItemClick(movie) },
        onClearClick = { onClearWatchedBtnClick() }
    )
    private val historyAdapter = HistoryAdapter(
        onInterestingItemClick = { movie -> onInterestingItemClick(movie) },
        onClearInterestingClick = { onClearInterestingBtnClick() }
    )
    private val collectionAdapter = CustomCollectionAdapter(
        onCollectionItemClick = { customCollection -> onCollectionItemClick(customCollection) },
        onDeleteCollectionClick = { collectionName -> onDeleteCollectionButtonClick(collectionName) }
    )
    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()
        createNewCollection()                                  // Create collection
        getMoviesFromCustomCollection()                        // Custom collection
        getMoviesFromHistory()                                 // History
        getMoviesFromWatched()                                 // Watched
        getMovieFromFavorites()                                // Favorites
        getMovieFromWishToWatch()                              // Wish to watch
    }

    private fun setAdapters() {
        binding.apply {
            watchedRecyclerView.adapter = watchedAdapter
            historyRecyclerView.adapter = historyAdapter
            containerLayoutForCustomCollections.adapter = collectionAdapter
        }
    }

    private fun getMoviesFromCustomCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                    profileMovieViewModel.getCustomCollectionNames(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                    profileMovieViewModel.getCustomCollections(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //1.0 the _customCollections.value stored list.size instead id
                // path: ProfileMovieViewModel.getCustomCollections
                profileMovieViewModel.customCollections.collectLatest { customCollectionList ->
                    if (customCollectionList.isNotEmpty()) {
                        binding.containerLayoutForCustomCollections.isVisible = true
                        collectionAdapter.submitList(customCollectionList)
                    } else binding.containerLayoutForCustomCollections.isVisible = false
                }
            }
        }
    }

    private fun getMoviesFromHistory() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllInteresting().collectLatest { list ->
                    profileMovieViewModel.buildInterestingList(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllInteresting().collectLatest { list ->
                    binding.allHistoryNumber.text = list.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.interestingList.collectLatest { list ->
                    if (list.isNotEmpty()) {
                        binding.historyRecyclerView.isVisible = true
                        historyAdapter.submitList(list.take(11))
                    } else binding.historyRecyclerView.isVisible = false
                }
            }
        }
        binding.allHistoryNumber.setOnClickListener {
            extendHistory()
        }
        binding.extendHistory.setOnClickListener {
            extendHistory()
        }
        binding.historyTitle.setOnClickListener {
            extendHistory()
        }
    }

    private fun getMoviesFromWatched() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllWatched().collectLatest { list ->
                    profileMovieViewModel.buildWatchedList(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllWatched().collectLatest { list ->
                    binding.allWatchedNumber.text = list.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.watchedList.collectLatest { list ->
                    if (list.isNotEmpty()) {
                        binding.watchedRecyclerView.isVisible = true
                        watchedAdapter.submitList(list.take(11))
                    } else binding.watchedRecyclerView.isVisible = false
                }
            }
        }
        binding.allWatchedNumber.setOnClickListener {
            extendWatched()
        }
        binding.extendWatched.setOnClickListener {
            extendWatched()
        }
        binding.watchedTitle.setOnClickListener {
            extendWatched()
        }
    }

    private fun getMovieFromWishToWatch() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllToWatch().collectLatest { list ->
                    binding.numberInToWatchCollection.text = list.size.toString()
                }
            }
        }
        binding.toWatchCollection.setOnClickListener {
            profileMovieViewModel.chooseCollection(Collections.ToWatch)
            findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
        }
    }

    private fun getMovieFromFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllFavorites().collectLatest { list ->
                    binding.numberInFavoritesCollection.text = list.size.toString()
                }
            }
        }
        binding.favoritsCollection.setOnClickListener {
            profileMovieViewModel.chooseCollection(Collections.Favorites)
            findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
        }
    }

    private fun createNewCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                    binding.createCustomCollectionText.setOnClickListener {
                        createNewCollection(list)
                    }
                    binding.createCollectionButton.setOnClickListener {
                        createNewCollection(list)
                    }
                }
            }
        }
    }

    private fun onClearWatchedBtnClick() {
        profileMovieViewModel.onClearWatched()
        binding.watchedRecyclerView.isVisible = false
    }

    private fun onWatchedItemClick(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            ProfileFragmentDirections.actionNavigationProfileToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }

    private fun onClearInterestingBtnClick() {
        profileMovieViewModel.onClearInteresting()
        binding.historyRecyclerView.isVisible = false
    }

    private fun onInterestingItemClick(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            ProfileFragmentDirections.actionNavigationProfileToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }

    private fun extendWatched() {
        findNavController().navigate(R.id.action_navigation_profile_to_watchedFragment)
    }

    private fun extendHistory() {
        findNavController().navigate(R.id.action_navigation_profile_to_interestingFragment)
    }

    private fun onDeleteCollectionButtonClick(collectionName: String) {
        profileMovieViewModel.deleteCollection(collectionName)
    }

    private fun onCollectionItemClick(customCollection: CustomCollection) {
        profileMovieViewModel.onCustomCollectionClick(customCollection)
        profileMovieViewModel.chooseCollection(Collections.Custom)
        findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
    }

    @SuppressLint("InflateParams")
    private fun createNewCollection(list: List<CustomCollection>) {
        profileMovieViewModel.getCustomCollectionNames(list)
        val dialog = Dialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val collectionTitleInputField =
            dialogView.findViewById<AppCompatEditText>(R.id.collection_title_input_field)
        val closeButton = dialogView.findViewById<AppCompatImageButton>(R.id.close_button)
        val doneButton = dialogView.findViewById<AppCompatButton>(R.id.done_button)

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        collectionTitleInputField.doOnTextChanged { charSequence, _, _, _ ->
            if (charSequence != null) {
                if (charSequence.count() >= 3) {
                    doneButton.isActivated = true
                    doneButton.isClickable = true
                    doneButton.isEnabled = true
                } else {
                    doneButton.isActivated = false
                    doneButton.isClickable = false
                    doneButton.isEnabled = false
                }
            }
        }

        doneButton.setOnClickListener {
            val collectionNameInput = collectionTitleInputField.text
            val collectionNameFormatted = collectionNameInput
                .toString()
                .trim { it <= ' ' }
                .lowercase(Locale.ROOT)
                .replaceFirstChar { char -> char.uppercaseChar() }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val tempListOfNames = profileMovieViewModel.customCollectionNamesList.value
                    if (!tempListOfNames.all { it != collectionNameFormatted }) {
                        dialog.dismiss()
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val tempList = profileMovieViewModel.customCollectionNamesList.value
                    if (tempList.all { it != collectionNameFormatted }) {
                        profileMovieViewModel.addMovieToCustomCollection(
                            collectionNameFormatted,
                            0
                        )
                        val customCollectionView =
                            layoutInflater.inflate(
                                R.layout.custom_collection_in_profile,
                                null
                            )
                        customCollectionView.id = View.generateViewId()
                        dialog.dismiss()
                    }
                }
            }
        }

        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}