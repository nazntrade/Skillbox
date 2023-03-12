package com.becker.beckerSkillCinema.presentation.profile

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.localData.entities.CustomCollection
import com.becker.beckerSkillCinema.data.localData.entities.Movie
import com.becker.beckerSkillCinema.data.profile.Collections
import com.becker.beckerSkillCinema.databinding.FragmentProfileBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.filmDetail.FilmDetailViewModel
import com.becker.beckerSkillCinema.presentation.home.HomeFragmentDirections
import com.becker.beckerSkillCinema.presentation.profile.customCollection.adapter.CustomCollectionAdapter
import com.becker.beckerSkillCinema.presentation.profile.interesting.adapter.WishMoviesAdapter
import com.becker.beckerSkillCinema.presentation.profile.watched.adapter.WatchedAdapterCommon
import kotlinx.coroutines.flow.collectLatest
import java.util.*

class ProfileFragment :
    ViewBindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

////////////////////////////// here many superfluous////////////////////////////////
    private lateinit var loader: AppCompatImageView
    private lateinit var allWatchedNumber: AppCompatButton
    private lateinit var extendWatched: AppCompatImageButton
    private lateinit var watchedRecyclerView: RecyclerView
    private lateinit var createCollectionButton: AppCompatImageButton
    private lateinit var createCollection: AppCompatButton
    private lateinit var numberInFavoritesCollection: AppCompatTextView
    private lateinit var numberInToWatchCollection: AppCompatTextView
    private lateinit var allInterestingNumber: AppCompatButton
    private lateinit var extendInteresting: AppCompatImageButton
    private lateinit var interestingRecyclerView: RecyclerView
    private lateinit var favoritesCollection: FrameLayout
    private lateinit var toWatchCollection: FrameLayout
    private lateinit var collectionRecyclerView: RecyclerView

    private val watchedAdapter = WatchedAdapterCommon(
        onWatchedItemClick = { movie -> onWatchedItemClick(movie) },
        onClearHistoryClick = { onClearWatchedClick() }
    )

    private val wishMoviesAdapter = WishMoviesAdapter(
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

        loader = binding.loader
        allWatchedNumber = binding.allWatchedNumber
        extendWatched = binding.extendWatched
        watchedRecyclerView = binding.watchedRecyclerView
        createCollectionButton = binding.createCollectionButton
        createCollection = binding.createCustomCollectionText
        numberInFavoritesCollection = binding.numberInFavoritesCollection
        numberInToWatchCollection = binding.numberInToWatchCollection
        allInterestingNumber = binding.allInterestingNumber
        extendInteresting = binding.extendInteresting
        interestingRecyclerView = binding.interestingRecyclerView
        favoritesCollection = binding.favoritsCollection
        toWatchCollection = binding.toWatchCollection
        collectionRecyclerView = binding.containerLayoutForCustomCollections

        watchedRecyclerView.adapter = watchedAdapter
        interestingRecyclerView.adapter = wishMoviesAdapter
        collectionRecyclerView.adapter = collectionAdapter


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                createCollection.setOnClickListener {
                    createCollection(list)
                }
                createCollectionButton.setOnClickListener {
                    createCollection(list)
                }
            }
        }


        favoritesCollection.setOnClickListener {
            profileMovieViewModel.chooseCollection(Collections.Favorites)
            findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
        }

        toWatchCollection.setOnClickListener {
            profileMovieViewModel.chooseCollection(Collections.ToWatch)
            findNavController().navigate(R.id.action_navigation_profile_to_profileCollectionFragment)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                profileMovieViewModel.getCustomCollectionNames(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                profileMovieViewModel.getCustomCollections(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllInteresting().collectLatest { list ->
                profileMovieViewModel.buildInterestingList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllWatched().collectLatest { list ->
                profileMovieViewModel.buildWatchedList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllWatched().collectLatest {
                allWatchedNumber.text = it.size.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllInteresting().collectLatest {
                allInterestingNumber.text = it.size.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.customCollections.collectLatest {
                if (it.isNotEmpty()) {
                    collectionRecyclerView.isVisible = true
                    collectionAdapter.submitList(it)
                } else collectionRecyclerView.isVisible = false
            }
        }

        allWatchedNumber.setOnClickListener {
            extendWatched()
        }
        extendWatched.setOnClickListener {
            extendWatched()
        }

        allInterestingNumber.setOnClickListener {
            extendInteresting()
        }
        extendInteresting.setOnClickListener {
            extendInteresting()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllFavorites().collectLatest {
                if (it.isNotEmpty()) {
                    numberInFavoritesCollection.isVisible = true
                    numberInFavoritesCollection.text = it.size.toString()
                } else numberInFavoritesCollection.isVisible = false
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllToWatch().collectLatest {
                if (it.isNotEmpty()) {
                    numberInToWatchCollection.isVisible = true
                    numberInToWatchCollection.text = it.size.toString()
                } else numberInToWatchCollection.isVisible = false
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.interestingList.collectLatest {
                if (it.isNotEmpty()) {
                    interestingRecyclerView.isVisible = true
                    wishMoviesAdapter.submitList(it.take(11))
                } else interestingRecyclerView.isVisible = false
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.watchedList.collectLatest {
                if (it.isNotEmpty()) {
                    watchedRecyclerView.isVisible = true
                    watchedAdapter.submitList(it.take(11))
                } else watchedRecyclerView.isVisible = false
            }
        }
    }

    private fun onClearWatchedClick() {
        profileMovieViewModel.onCleanWatchedClick()
        watchedRecyclerView.isVisible = false
    }

    private fun onWatchedItemClick(movie: Movie) {/////////////////////////////
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            ProfileFragmentDirections.actionNavigationProfileToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }

    private fun onClearInterestingClick() {
        profileMovieViewModel.onCleanInterestingClick()
        interestingRecyclerView.isVisible = false
    }

    private fun onInterestingItemClick(movie: Movie) {////////////////////////
        filmDetailViewModel.putFilmId(movie.movieId)
        val action =
            ProfileFragmentDirections.actionNavigationProfileToFragmentFilmDetail(movie.movieId)
        findNavController().navigate(action)
    }

    private fun extendWatched() {
        findNavController().navigate(R.id.action_navigation_profile_to_watchedFragment)
    }

    private fun extendInteresting() {
        findNavController().navigate(R.id.action_navigation_profile_to_interestingFragment)
    }

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

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val list = profileMovieViewModel.customCollectionNamesList.value
                if (!list.all { it != collectionNameFormatted }) {
                    dialog.dismiss()
//                    showErrorWarning()
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val list = profileMovieViewModel.customCollectionNamesList.value

                if (list.all { it != collectionNameFormatted }) {
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