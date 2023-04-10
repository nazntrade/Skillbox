package com.becker.beckerSkillCinema.presentation.profile


import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.*
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentCollectionHandlerBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CollectionHandlerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCollectionHandlerBinding? = null
    private val binding get() = _binding!!

    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionHandlerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCurrentMovieFromDB()
        getAndCreateCustomCollection()
        getToWatchCollection()
        getFavoritesCollection()
        binding.crossButton.setOnClickListener {
            dismiss()
        }
    }

    private fun getCurrentMovieFromDB() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.movieInfo.collectLatest { responseCurrentFilm ->
                    profileMovieViewModel.movieById.collectLatest { movieFromDB ->
                        if (movieFromDB != null) {
                            Glide
                                .with(binding.moviePoster.context)
                                .load(movieFromDB.posterUri)
                                .centerCrop()
                                .into(binding.moviePoster)
                            binding.filmRating.text = movieFromDB.rating.toString()
                            binding.filmTitle.text = movieFromDB.movieName
                            binding.filmInfo.text = buildString {
                                append(movieFromDB.year ?: "")
                                val genre = movieFromDB.genre
                                if (genre != null) {
                                    append(", ")
                                    append(genre)
                                }
                            }
                        } else {
                            if (responseCurrentFilm != null) {
                                Glide
                                    .with(binding.moviePoster.context)
                                    .load(responseCurrentFilm.posterUrl)
                                    .centerCrop()
                                    .into(binding.moviePoster)
                                binding.filmRating.text =
                                    when {
                                        responseCurrentFilm.ratingKinopoisk != null ->
                                            responseCurrentFilm.ratingKinopoisk.toString()
                                        responseCurrentFilm.ratingImdb != null ->
                                            responseCurrentFilm.ratingImdb.toString()
                                        responseCurrentFilm.ratingMpaa != null ->
                                            responseCurrentFilm.ratingMpaa
                                        else -> ""
                                    }
                                binding.filmTitle.text =
                                    responseCurrentFilm.nameRu ?: responseCurrentFilm.nameEn
                                            ?: responseCurrentFilm.nameOriginal ?: ""
                                binding.filmInfo.text = buildString {
                                    append(
                                        responseCurrentFilm.year ?: responseCurrentFilm.startYear
                                        ?: responseCurrentFilm.endYear ?: ""
                                    )
                                    val genre = responseCurrentFilm.genres.firstOrNull()?.genre
                                    if (genre != null) {
                                        append(", ")
                                        append(genre)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAndCreateCustomCollection() {
        binding.createCustomCollectionLayout.setOnClickListener {
            showDialogToCreateCustomCollection()
        }
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
                profileMovieViewModel.movieSelected.collectLatest { movieId ->
                    profileMovieViewModel.customCollections.collectLatest { listCollections ->
                        listCollections.forEachIndexed { index, customCollection ->
                            val customCollectionView =
                                inflateCustomCollectionView(
                                    customCollection.collectionName,
                                    movieId,
                                    index,
                                    listCollections.size,
                                    customCollection.movieId
                                )
                            customCollectionView.contentDescription =
                                customCollection.collectionName
                            binding.containerLayoutForCustomCollection.isVisible = true
                            if (binding.containerLayoutForCustomCollection.children.all {
                                    it.contentDescription != customCollectionView.contentDescription
                                }) {
                                binding.containerLayoutForCustomCollection.addView(
                                    customCollectionView
                                )
                            } else {
                                val existingView =
                                    binding.containerLayoutForCustomCollection.children.find {
                                        it.contentDescription == customCollectionView.contentDescription
                                    }
                                binding.containerLayoutForCustomCollection.removeView(existingView)
                                binding.containerLayoutForCustomCollection.addView(
                                    customCollectionView
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getToWatchCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllToWatch().collectLatest {
                    binding.numberToWatch.text = it.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.addedToWatch.collectLatest {
                    binding.checkboxIconToWatch.isActivated = it
                }
            }
        }
        binding.checkboxIconToWatch.setOnCheckedChangeListener { _, isChecked ->
            binding.checkboxIconToWatch.isActivated = isChecked
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.movieSelected.collectLatest {
                        profileMovieViewModel.doOnBookmarkBtnClick(it)
                    }
                }
            }
        }
    }

    private fun getFavoritesCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllFavorites().collectLatest {
                    binding.numberFavorites.text = it.size.toString()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.addedToFavorites.collectLatest {
                    binding.checkboxIconFavorites.isActivated = it
                }
            }
        }
        binding.checkboxIconFavorites.setOnCheckedChangeListener { _, isChecked ->
            binding.checkboxIconFavorites.isActivated = isChecked
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.movieSelected.collectLatest {
                        profileMovieViewModel.onFavoritesButtonClick(it)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun showDialogToCreateCustomCollection() {
        val dialog = Dialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val collectionTitleInputField =
            dialogView.findViewById<AppCompatEditText>(R.id.collection_title_input_field)
        val closeButtonCreateDialog =
            dialogView.findViewById<AppCompatImageButton>(R.id.close_button)
        val doneButtonCreateDialog = dialogView.findViewById<AppCompatButton>(R.id.done_button)

        closeButtonCreateDialog.setOnClickListener {
            dialog.dismiss()
        }

        collectionTitleInputField.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.count() >= 3) {
                    doneButtonCreateDialog.isActivated = true
                    doneButtonCreateDialog.isClickable = true
                    doneButtonCreateDialog.isEnabled = true
                } else {
                    doneButtonCreateDialog.isActivated = false
                    doneButtonCreateDialog.isClickable = false
                    doneButtonCreateDialog.isEnabled = false
                }
            }
        }

        doneButtonCreateDialog.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.movieSelected.collectLatest { movieId ->
                        val collectionNameInput = collectionTitleInputField.text
                        val collectionNameFormatted = collectionNameInput.toString()
                            .trim { it <= ' ' }
                            .lowercase(Locale.ROOT)
                            .replaceFirstChar { it.uppercaseChar() }

                        viewLifecycleOwner.lifecycleScope.launch {
                            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                val listOfNames =
                                    profileMovieViewModel.customCollectionNamesList.value
                                if (!listOfNames.all { it != collectionNameFormatted }) {
                                    dialog.dismiss()
                                }
                            }
                        }
                        // add movie to custom collection
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                val listOfNames =
                                    profileMovieViewModel.customCollectionNamesList.value
                                if (listOfNames.all { it != collectionNameFormatted }) {
                                    profileMovieViewModel.addMovieToCustomCollection(
                                        collectionName = collectionNameFormatted,
                                        movieId = movieId
                                    )
                                    dialog.dismiss()
                                }
                            }
                        }
                    }
                    dialog.dismiss()
                }
            }
        }
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    @SuppressLint("InflateParams")
    private fun inflateCustomCollectionView(
        collectionNameFormatted: String,
        movieId: Int,
        index: Int,
        collectionsCount: Int,
        collectionSize: Int
    ): View {
        val customCollectionView = layoutInflater.inflate(R.layout.custom_collection, null)

        val nameToDisplay =
            customCollectionView.findViewById<AppCompatTextView>(R.id.custom_collection_title)
        nameToDisplay.text = collectionNameFormatted

        val countMovieInCollection =
            customCollectionView.findViewById<AppCompatTextView>(R.id.count_in_custom_collection)

        countMovieInCollection.text = (collectionSize - 1).toString()

        val checkbox =
            customCollectionView.findViewById<AppCompatCheckBox>(R.id.checkbox_custom_collection)
        checkbox.contentDescription = collectionNameFormatted

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllMoviesFromCustomCollection()
                    .collectLatest { listMovies ->
                        profileMovieViewModel.checkCustomCollection(
                            collectionNameFormatted,
                            movieId,
                            index,
                            collectionsCount,
                            listMovies
                        )
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.addedToCustomCollection.collectLatest { collectionStatus ->
                    checkbox.isActivated = collectionStatus[checkbox.contentDescription] == true
                }
            }
        }

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkbox.isActivated = isChecked
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.onCustomCollectionButtonClick(
                        checkbox.contentDescription.toString(),
                        movieId
                    )
                }
            }
        }

        return customCollectionView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}