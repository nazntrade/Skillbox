package com.becker.beckerSkillCinema.presentation.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
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

class ProfileFragment :
    ViewBindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var loader: AppCompatImageView
    private lateinit var allWatchedNumber: AppCompatButton
    private lateinit var extendWatched: AppCompatImageButton
    private lateinit var extendWatchedTitle: AppCompatTextView
    private lateinit var watchedRecyclerView: RecyclerView
    private lateinit var createCollectionButton: AppCompatImageButton
    private lateinit var createCollectionText: AppCompatButton
    private lateinit var numberInFavoritesCollection: AppCompatTextView
    private lateinit var numberInToWatchCollection: AppCompatTextView
    private lateinit var allHistoryNumber: AppCompatButton
    private lateinit var extendHistory: AppCompatImageButton
    private lateinit var historyTitle: AppCompatTextView
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var favoritesCollection: FrameLayout
    private lateinit var toWatchCollection: FrameLayout
    private lateinit var collectionRecyclerView: RecyclerView

    private val watchedAdapter = WatchedAdapterCommon(
        onWatchedItemClick = { movie -> onWatchedItemClick(movie) },
        onClearHistoryClick = { onClearWatchedClick() }
    )
    private val historyAdapter = HistoryAdapter(
        onInterestingItemClick = { movie -> onInterestingItemClick(movie) },
        onClearInterestingClick = { onClearInterestingClick() }
    )
    private val collectionAdapter = CustomCollectionAdapter(
        onCollectionItemClick = { customCollection -> onCollectionItemClick(customCollection) },
        onDeleteCollectionClick = { collectionName -> onDeleteCollectionButtonClick(collectionName) }
    )
    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private val filmDetailViewModel: FilmDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLayoutElements()
        setAdapters()
        setListeners()
        getMoviesFromCollections()
    }

    private fun getLayoutElements() {
        loader = binding.loader
        allWatchedNumber = binding.allWatchedNumber
        extendWatched = binding.extendWatched
        extendWatchedTitle = binding.watchedTitle
        watchedRecyclerView = binding.watchedRecyclerView
        createCollectionButton = binding.createCollectionButton
        createCollectionText = binding.createCustomCollectionText
        numberInFavoritesCollection = binding.numberInFavoritesCollection
        numberInToWatchCollection = binding.numberInToWatchCollection
        allHistoryNumber = binding.allHistoryNumber
        extendHistory = binding.extendHistory
        historyTitle = binding.historyTitle
        historyRecyclerView = binding.historyRecyclerView
        favoritesCollection = binding.favoritsCollection
        toWatchCollection = binding.toWatchCollection
        collectionRecyclerView = binding.containerLayoutForCustomCollections
    }

    private fun setAdapters() {
        watchedRecyclerView.adapter = watchedAdapter
        historyRecyclerView.adapter = historyAdapter
        collectionRecyclerView.adapter = collectionAdapter
    }

    private fun getMoviesFromCollections() {
        //AllMoviesFromCustomCollection
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
                profileMovieViewModel.customCollections.collectLatest {
                    if (it.isNotEmpty()) {
                        collectionRecyclerView.isVisible = true
                        collectionAdapter.submitList(it)
                    } else collectionRecyclerView.isVisible = false
                }
            }
        }
        //get all movie from history
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllInteresting().collectLatest { list ->
                    profileMovieViewModel.buildInterestingList(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllInteresting().collectLatest {
                    allHistoryNumber.text = it.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.interestingList.collectLatest {
                    if (it.isNotEmpty()) {
                        historyRecyclerView.isVisible = true
                        historyAdapter.submitList(it.take(11))
                    } else historyRecyclerView.isVisible = false
                }
            }
        }
        //get watched
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllWatched().collectLatest { list ->
                    profileMovieViewModel.buildWatchedList(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllWatched().collectLatest {
                    allWatchedNumber.text = it.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.watchedList.collectLatest {
                    if (it.isNotEmpty()) {
                        watchedRecyclerView.isVisible = true
                        watchedAdapter.submitList(it.take(11))
                    } else watchedRecyclerView.isVisible = false
                }
            }
        }
        //To Watch
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllToWatch().collectLatest {
                    if (it.isNotEmpty()) {
                        numberInToWatchCollection.isVisible = true
                        numberInToWatchCollection.text = it.size.toString()
                    } else numberInToWatchCollection.isVisible = false
                }
            }
        }
        //Favorites
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllFavorites().collectLatest {
                    if (it.isNotEmpty()) {
                        numberInFavoritesCollection.isVisible = true
                        numberInFavoritesCollection.text = it.size.toString()
                    } else numberInFavoritesCollection.isVisible = false
                }
            }
        }
    }

    private fun setListeners() {
        //Create collection
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                    createCollectionText.setOnClickListener {
                        createCollection(list)
                    }
                    createCollectionButton.setOnClickListener {
                        createCollection(list)
                    }
                }
            }
        }
        //Choose collection
        favoritesCollection.setOnClickListener {
            profileMovieViewModel.chooseCollection(Collections.Favorites)
            findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
        }
        toWatchCollection.setOnClickListener {
            profileMovieViewModel.chooseCollection(Collections.ToWatch)
            findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
        }
        //Watched
        allWatchedNumber.setOnClickListener {
            extendWatched()
        }
        extendWatched.setOnClickListener {
            extendWatched()
        }
        extendWatchedTitle.setOnClickListener {
            extendWatched()
        }
        //Interesting
        allHistoryNumber.setOnClickListener {
            extendHistory()
        }
        extendHistory.setOnClickListener {
            extendHistory()
        }
        historyTitle.setOnClickListener {
            extendHistory()
        }
    }

    private fun onClearWatchedClick() {
        profileMovieViewModel.onCleanWatchedClick()
        watchedRecyclerView.isVisible = false
    }

    private fun onWatchedItemClick(movie: Movie) {
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            ProfileFragmentDirections.actionNavigationProfileToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }

    private fun onClearInterestingClick() {
        profileMovieViewModel.onCleanInterestingClick()
        historyRecyclerView.isVisible = false
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

    @SuppressLint("InflateParams")
    private fun createCollection(list: List<CustomCollection>) {
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

        collectionTitleInputField.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.count() >= 3) {
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
            val collectionNameFormatted = collectionNameInput.toString()
                .trim { it <= ' ' }
                .lowercase(Locale.ROOT)
                .replaceFirstChar { it.uppercaseChar() }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val tempList = profileMovieViewModel.customCollectionNamesList.value
                    if (!tempList.all { it != collectionNameFormatted }) {
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

    private fun onDeleteCollectionButtonClick(collectionName: String) {
        profileMovieViewModel.onDeleteCollectionButtonClick(collectionName)
    }

    private fun onCollectionItemClick(customCollection: CustomCollection) {
        profileMovieViewModel.onCustomCollectionClick(customCollection)
        profileMovieViewModel.chooseCollection(Collections.Custom)
        findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
    }
}

//viewLifecycleOwner.lifecycleScope.launch {
//                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {