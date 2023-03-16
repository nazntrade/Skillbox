package com.becker.beckerSkillCinema.presentation.profile


import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.*
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.databinding.FragmentCollectionHandlerBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@AndroidEntryPoint
class CollectionHandlerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCollectionHandlerBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviePoster: AppCompatImageView
    private lateinit var movieRating: AppCompatTextView
    private lateinit var movieTitle: AppCompatTextView
    private lateinit var movieInfo: AppCompatTextView
    private lateinit var closeButton: AppCompatImageButton
    private lateinit var addToCollectionButton: AppCompatImageButton
    private lateinit var checkBoxFavorites: AppCompatCheckBox
    private lateinit var numberFavorites: AppCompatTextView
    private lateinit var checkBoxToWatch: AppCompatCheckBox
    private lateinit var numberToWatch: AppCompatTextView
    private lateinit var createCustomCollectionLayout: LinearLayout
    private lateinit var containerLayoutForCustomCollection: LinearLayout

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

        getLayoutElements()
        getCurrentMovie()
        setOnClickListeners()
        setCollectionsData()
    }

    private fun getLayoutElements() {
        moviePoster = binding.moviePoster
        movieRating = binding.filmRating
        movieTitle = binding.filmTitle
        movieInfo = binding.filmInfo
        closeButton = binding.crossButton
        addToCollectionButton = binding.addToCollectionIcon
        checkBoxFavorites = binding.checkboxIconFavorites
        numberFavorites = binding.numberFavorites
        checkBoxToWatch = binding.checkboxIconToWatch
        numberToWatch = binding.numberToWatch
        createCustomCollectionLayout = binding.createCustomCollectionLayout
        containerLayoutForCustomCollection = binding.containerLayoutForCustomCollection
    }

    private fun getCurrentMovie() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.movieInfo.collectLatest {
                profileMovieViewModel.movieById.collectLatest { movieFromDB ->
                    if (movieFromDB != null) {
                        Glide
                            .with(moviePoster.context)
                            .load(movieFromDB.posterUri)
                            .centerCrop()
                            .into(moviePoster)
                        movieRating.text = movieFromDB.rating.toString()
                        movieTitle.text = movieFromDB.movieName
                        movieInfo.text = buildString {
                            append(movieFromDB.year ?: "")
                            val genre = movieFromDB.genre
                            if (genre != null) {
                                append(", ")
                                append(genre)
                            }
                        }
                    } else {
                        if (it != null) {
                            Glide
                                .with(moviePoster.context)
                                .load(it.posterUrl)
                                .centerCrop()
                                .into(moviePoster)
                            movieRating.text =
                                when {
                                    it.ratingKinopoisk != null -> it.ratingKinopoisk.toString()
                                    it.ratingImdb != null -> it.ratingImdb.toString()
                                    it.ratingMpaa != null -> it.ratingMpaa
                                    else -> ""
                                }
                            movieTitle.text = it.nameRu ?: it.nameEn ?: it.nameOriginal ?: ""
                            movieInfo.text = buildString {
                                append(it.year ?: it.startYear ?: it.endYear ?: "")
                                val genre = it.genres.firstOrNull()?.genre
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

    private fun setOnClickListeners() {
        checkBoxFavorites.setOnCheckedChangeListener { _, isChecked ->
            checkBoxFavorites.isActivated = isChecked
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                profileMovieViewModel.movieSelected.collectLatest {
                    profileMovieViewModel.onFavoritesButtonClick(it)
                }
            }
        }
        checkBoxToWatch.setOnCheckedChangeListener { _, isChecked ->
            checkBoxToWatch.isActivated = isChecked
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                profileMovieViewModel.movieSelected.collectLatest {
                    profileMovieViewModel.doOnBookmarkBtnClick(it)
                }
            }
        }
        createCustomCollectionLayout.setOnClickListener {
            createDialog()
        }
        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setCollectionsData() {
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
            profileMovieViewModel.movieSelected.collectLatest { movieId ->
                profileMovieViewModel.customCollections.collectLatest { list ->
                    list.forEachIndexed { index, customCollection ->
                        val customCollectionView =
                            inflateView(
                                customCollection.collectionName,
                                movieId,
                                index,
                                list.size,
                                customCollection.movieId
                            )
                        customCollectionView.contentDescription = customCollection.collectionName
                        containerLayoutForCustomCollection.isVisible = true
                        if (containerLayoutForCustomCollection.children.all {
                                it.contentDescription != customCollectionView.contentDescription
                            }) {
                            containerLayoutForCustomCollection.addView(customCollectionView)
                        } else {
                            val existingView =
                                containerLayoutForCustomCollection.children.find {
                                    it.contentDescription == customCollectionView.contentDescription
                                }
                            containerLayoutForCustomCollection.removeView(existingView)
                            containerLayoutForCustomCollection.addView(customCollectionView)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllToWatch().collectLatest {
                numberToWatch.text = it.size.toString()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllFavorites().collectLatest {
                numberFavorites.text = it.size.toString()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.addedToFavorites.collectLatest {
                checkBoxFavorites.isActivated = it
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.addedToWatch.collectLatest {
                checkBoxToWatch.isActivated = it
            }
        }
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    private fun createDialog() {
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
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                profileMovieViewModel.movieSelected.collectLatest { movieId ->
                    val collectionNameInput = collectionTitleInputField.text
                    val collectionNameFormatted = collectionNameInput.toString()
                        .trim { it <= ' ' }
                        .lowercase(Locale.ROOT)
                        .replaceFirstChar { it.uppercaseChar() }
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        val list = profileMovieViewModel.customCollectionNamesList.value
                        if (!list.all { it != collectionNameFormatted }) {
                            dialog.dismiss()
                        }
                    }
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        val list = profileMovieViewModel.customCollectionNamesList.value
                        if (list.all { it != collectionNameFormatted }) {
                            profileMovieViewModel.addMovieToCustomCollection(
                                collectionNameFormatted,
                                movieId
                            )
                            dialog.dismiss()
                        }
                    }
                }
                dialog.dismiss()
            }
        }
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    @SuppressLint("InflateParams")
    private fun inflateView(
        collectionNameFormatted: String,
        movieId: Int,
        index: Int,
        collectionNumber: Int,
        collectionSize: Int
    ): View {
        val customCollectionView =
            layoutInflater.inflate(
                R.layout.custom_collection,
                null
            )

        val nameToDisplay =
            customCollectionView.findViewById<AppCompatTextView>(R.id.custom_collection_title)
        nameToDisplay.text = collectionNameFormatted

        val numberInCollection =
            customCollectionView.findViewById<AppCompatTextView>(R.id.number_in_custom_collection)

        numberInCollection.text = collectionSize.toString()

        val checkbox =
            customCollectionView.findViewById<AppCompatCheckBox>(R.id.checkbox_custom_collection)
        checkbox.contentDescription = collectionNameFormatted
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.getAllMoviesFromCustomCollection().collectLatest { list ->
                profileMovieViewModel.checkCustomCollection(
                    collectionNameFormatted,
                    movieId,
                    index,
                    collectionNumber,
                    list
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileMovieViewModel.addedToCustomCollection.collectLatest { collectionStatus ->
                checkbox.isActivated = collectionStatus[checkbox.contentDescription] == true
            }
        }

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkbox.isActivated = isChecked
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                profileMovieViewModel.onCustomCollectionButtonClick(
                    checkbox.contentDescription.toString(),
                    movieId
                )
            }
        }
        return customCollectionView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
