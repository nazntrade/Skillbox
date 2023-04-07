package com.becker.beckerSkillCinema.presentation.filmDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.CategoriesFilms
import com.becker.beckerSkillCinema.data.Professions
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.databinding.FragmentFilmDetailBinding
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.homeAdapters.filmAdapter.FilmAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.launch
import com.becker.beckerSkillCinema.presentation.filmDetail.gallery.galleryAdapter.GalleryAdapter
import com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffAdapter.StaffAdapter
import com.becker.beckerSkillCinema.presentation.profile.CollectionHandlerFragment
import com.becker.beckerSkillCinema.presentation.profile.ProfileMovieViewModel
import com.becker.beckerSkillCinema.utils.ConstantsAndParams.TOP_TYPES
import com.becker.beckerSkillCinema.utils.loadImage
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class FragmentFilmDetail :
    ViewBindingFragment<FragmentFilmDetailBinding>(FragmentFilmDetailBinding::inflate) {

    // https://slack-chats.kotlinlang.org/t/471784/can-anyone-explain-what-is-by-activityviewmodels-by-fragment
    private val viewModel: FilmDetailViewModel by activityViewModels()
    private val profileMovieViewModel: ProfileMovieViewModel by activityViewModels()
    private var actorAdapter: StaffAdapter by autoCleared()
    private var makersAdapter: StaffAdapter by autoCleared()
    private var galleryAdapter: GalleryAdapter by autoCleared()
    private var similarAdapter: FilmAdapter by autoCleared()
    private val incomeArgs: FragmentFilmDetailArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //to store current movie in viewModel
        profileMovieViewModel.movieSelected(incomeArgs.filmId)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        stateLoadingListener()              // Set listener downloads
        setFilmDetailsAndAddToDataBase()    // set Film Details And Add To DataBase
        setFilmActors()
        setFilmCrew()
        setFilmGallery()
        setSimilarFilms()
        actionOnPosterBtn()
    }


    private fun actionOnPosterBtn() {
        // add current film to history
        addCurrentFilmToHistory()
        //Btn heart
        checkOrDoOnHeartBtn()
        //Btn bookmark
        checkOrDoOnBookMarkBtn()
        //Btn watched or not watched
        checkOrDoOnWatchedBtn()
        //Btn SHARE
        doOnClickShareBtn()
        //Btn show more
        doOnClickShowMoreBtn()
    }

    private fun addCurrentFilmToHistory() {
        profileMovieViewModel.addCurrentFilmToHistory(incomeArgs.filmId)
    }

    private fun doOnClickShowMoreBtn() {
        binding.btnShowMore.setOnClickListener {
            profileMovieViewModel.getCurrentMovieFromDataBaseById(incomeArgs.filmId)
            onClickCollectionHandler()
        }
    }

    private fun doOnClickShareBtn() {
        binding.btnShare.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = INTENT_TYPE
            share.putExtra(
                Intent.EXTRA_TEXT, "https://www.kinopoisk.ru/film/${incomeArgs.filmId}/"
            )
            startActivity(Intent.createChooser(share, INTENT_TITLE))
        }
    }

    private fun checkOrDoOnHeartBtn() {
        //check favorites
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllFavorites().collectLatest { list ->
                    profileMovieViewModel.movieSelected.collectLatest { movieId ->
                        profileMovieViewModel.checkFavorites(movieId, list)
                    }
                }
            }
        }
        //add movie_favorites to data base (or delete if it already there)
        binding.apply {
            btnToFavorite.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        profileMovieViewModel.movieSelected.collectLatest { movieId ->
                            profileMovieViewModel.onFavoritesButtonClick(movieId)
                        }
                    }
                }
            }
            //toggle Btn state
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.addedToFavorites.collectLatest {
                        btnToFavorite.isActivated = it
                    }
                }
            }
        }
    }

    private fun checkOrDoOnBookMarkBtn() {
        //check toWatch (bookmark Btn)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllToWatch().collectLatest { list ->
                    profileMovieViewModel.movieSelected.collectLatest { movieId ->
                        profileMovieViewModel.checkToWatch(movieId, list)
                    }
                }
            }
        }
        //add movie_want_toWatch to data base (or delete if it already there)
        binding.apply {
            btnToBookmark.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        profileMovieViewModel.movieSelected.collectLatest { movieId ->
                            profileMovieViewModel.doOnBookmarkBtnClick(movieId)
                        }
                    }
                }
            }
            //toggle Btn state
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.addedToWatch.collectLatest {
                        btnToBookmark.isActivated = it
                    }
                }
            }
        }
    }

    private fun checkOrDoOnWatchedBtn() {
        //check watched
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileMovieViewModel.getAllWatched().collectLatest { list ->
                    profileMovieViewModel.movieSelected.collectLatest { movieId ->
                        profileMovieViewModel.checkWatched(movieId, list)
                    }
                }
            }
        }
        //add watched_movie to data base (or delete if it already there)
        binding.apply {
            btnIsWatched.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        profileMovieViewModel.movieSelected.collectLatest { movieId ->
                            profileMovieViewModel.onWatchedButtonClick(movieId)
                        }
                    }
                }
            }
            //toggle Btn state
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    profileMovieViewModel.addedToWatched.collectLatest {
                        btnIsWatched.isActivated = it
                    }
                }
            }
        }
    }

    private fun onClickCollectionHandler() {
        val popupWindow = CollectionHandlerFragment()
        popupWindow.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        popupWindow.enterTransition = com.google.android.material.R.id.animateToStart
        popupWindow.exitTransition = com.google.android.material.R.id.animateToEnd
        popupWindow.show(requireActivity().supportFragmentManager, POPUP_WINDOW_TAG)
    }

    private fun stateLoadingListener() {
        binding.progressGroupContainer.loadingRefreshBtn
            .setOnClickListener { viewModel.getFilmById() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingCurrentFilmState.collect { state ->
                    when (state) {
                        is StateLoading.Loading -> {
                            binding.apply {
                                filmDetailMotionLayout.isGone = true
                                progressGroupContainer.progressGroup.isVisible = true
                                progressGroupContainer.loadingProgressBar.isVisible = true
                                progressGroupContainer.loadingRefreshBtn.isVisible = false
                                progressGroupContainer.noAnswerText.isVisible = false
                            }
                        }
                        is StateLoading.Success -> {
                            binding.apply {
                                filmDetailMotionLayout.isGone = false
                                progressGroupContainer.progressGroup.isVisible = false
                                filmMainGroupWithPoster.isVisible = true
                                filmDescriptionGroup.isVisible = true
                            }
                        }
                        else -> {
                            binding.apply {
                                filmDetailMotionLayout.isGone = true
                                progressGroupContainer.progressGroup.isVisible = true
                                progressGroupContainer.loadingProgressBar.isVisible = false
                                progressGroupContainer.loadingRefreshBtn.isVisible = true
                                progressGroupContainer.noAnswerText.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }

    // About film
    private fun setFilmDetailsAndAddToDataBase() {
        if (incomeArgs.filmId != viewModel.currentFilmId) {
            viewModel.getFilmId()
            viewModel.getFilmById()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        try {
                            viewModel.currentFilm.collectLatest { film ->
                                if (film != null) {
                                    if (film.type == TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)) {
                                        binding.seasonsGroup.isVisible = true
                                        viewModel.getSeasons(film.kinopoiskId)
                                        getSeriesSeasons(getName(film))
                                    } else {
                                        binding.seasonsGroup.isVisible = false
                                    }
                                    binding.apply {
                                        val filmNameExtracted = getName(film)
                                        filmName.text = filmNameExtracted
                                        getVideo(filmNameExtracted)
                                        filmPoster.loadImage(film.posterUrl)
                                        filmDescriptionShort.text = film.shortDescription
                                        filmDescriptionFull.text = film.description
                                        filmRatingNameTv.text = getRatingName(film)
                                        filmYearGenresTv.text =
                                            getYearAndGenres(film, requireContext())
                                        filmCountryLengthAgeLimitTv.text =
                                            getStrCountriesLengthAge(film)
                                    }
                                    // Add film to DataBase !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                    profileMovieViewModel.addMovieToDataBase(film)
                                }
                            }
                        } catch (e: Throwable) {
                            Timber.e("setFilmDetails $e")
                        }
                    }
                }
            }
        }
    }

    // About season and series
    private fun getSeriesSeasons(seriesName: String) {
        binding.seasonsGroup.setOnClickListener { showAllSeasons(seriesName) }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewModel.seasons.collect { seasons ->
                    val seasonsCount = seasons.size
                    var episodesCount = 0
                    seasons.forEach { season ->
                        episodesCount += season.episodes.size
                    }
                    val seasonStr = resources.getQuantityString(
                        R.plurals.film_details_series_count,
                        seasonsCount,
                        seasonsCount
                    )
                    val episodeStr = resources.getQuantityString(
                        R.plurals.film_details_episode_count,
                        episodesCount,
                        episodesCount
                    )
                    binding.seriesSeasonsCount.text = resources.getString(
                        R.string.seasons_episodes_count,
                        seasonStr,
                        episodeStr
                    )
                }
            } catch (e: Throwable) {
                Timber.e("getSeriesSeasons $e")
            }
        }
    }

    private fun showAllSeasons(seriesName: String) {
        val action = FragmentFilmDetailDirections
            .actionFragmentFilmDetailToFragmentSeries(seriesName)
        findNavController().navigate(action)
    }

    // Actors and film crew
    private fun setFilmActors() {
        actorAdapter = StaffAdapter { onStaffClick(it) }

        binding.filmActorsList.layoutManager =
            GridLayoutManager(
                requireContext(), MAX_ACTORS_ROWS, GridLayoutManager.HORIZONTAL, false
            )

        binding.filmActorsList.adapter = actorAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFilmActors.collect { actorList ->
                    binding.filmActorsCount.text = actorList.size.toString()
                    if (actorList.size < MAX_ACTORS_COLUMN * MAX_ACTORS_ROWS) {
                        binding.filmActorsCount.isEnabled = false
                        binding.filmActorsBtn.isEnabled = false
                        actorAdapter.submitList(actorList)
                    } else {    // set only if fewer 20
                        binding.filmActorsCount.isEnabled = true
                        binding.filmActorsBtn.isEnabled = true
                        val actorsListTemp = mutableListOf<ResponseStaffByFilmId>()
                        repeat(MAX_ACTORS_COLUMN * MAX_ACTORS_ROWS) {
                            actorsListTemp.add(actorList[it])
                        }
                        actorAdapter.submitList(actorsListTemp)
                        binding.apply {
                            filmActorsLabel.setOnClickListener {
                                showAllStaffs(Professions.ACTOR.name)
                            }
                            filmActorsBtn.setOnClickListener {
                                showAllStaffs(Professions.ACTOR.name)
                            }
                            filmActorsCount.setOnClickListener {
                                showAllStaffs(Professions.ACTOR.name)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setFilmCrew() {
        makersAdapter = StaffAdapter { onStaffClick(it) }

        binding.filmMakersList.layoutManager =
            GridLayoutManager(
                requireContext(), MAX_MAKERS_ROWS, GridLayoutManager.HORIZONTAL, false
            )

        binding.filmMakersList.adapter = makersAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFilmMakers.collect { makersList ->
                    binding.filmMakersCount.text = makersList.size.toString()
                    if (makersList.size < MAX_MAKERS_COLUMN * MAX_MAKERS_ROWS) {
                        binding.filmMakersCount.isEnabled = false
                        binding.filmMakersBtn.isEnabled = false
                        makersAdapter.submitList(makersList)
                    } else {    // set only if fewer 6
                        binding.filmMakersCount.isEnabled = true
                        binding.filmMakersBtn.isEnabled = true
                        val makersListTemp = mutableListOf<ResponseStaffByFilmId>()
                        repeat(MAX_MAKERS_COLUMN * MAX_MAKERS_ROWS) { makersListTemp.add(makersList[it]) }
                        makersAdapter.submitList(makersListTemp)
                        binding.apply {
                            filmMakersLabel.setOnClickListener { showAllStaffs("") }
                            filmMakersBtn.setOnClickListener { showAllStaffs("") }
                            filmMakersCount.setOnClickListener { showAllStaffs("") }
                        }
                    }
                }
            }
        }
    }

    private fun onStaffClick(staff: ResponseStaffByFilmId) {
        val action =
            FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentStaffDetail(staff.staffId)
        findNavController().navigate(action)
    }

    private fun showAllStaffs(professionalKey: String) {
        val action = FragmentFilmDetailDirections
            .actionFragmentFilmDetailToFragmentAllStaffsByFilm(professionalKey)
        findNavController().navigate(action)
    }

    // Gallery
    private fun setFilmGallery() {
        galleryAdapter = GalleryAdapter(20, { showAllImages() }, { showClickedImage(it) })
        binding.filmGalleryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filmGalleryList.adapter = galleryAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFilmGallery.collect { responseGallery ->
                    galleryAdapter.submitList(responseGallery)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalNumberOfPictures.collect {
                    binding.filmGalleryCount.text = it.toString()
                    if (it > 20) {
                        binding.filmGalleryBtn.isEnabled = true
                        binding.filmGalleryCount.isEnabled = true
                    } else {
                        binding.filmGalleryBtn.isEnabled = false
                        binding.filmGalleryCount.isEnabled = false
                    }
                }
            }
        }
        binding.apply {
            filmGalleryLabel.setOnClickListener { showAllImages() }
            filmGalleryCount.setOnClickListener { showAllImages() }
            filmGalleryBtn.setOnClickListener { showAllImages() }
        }
    }

    private fun showAllImages() {
        findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentGallery)
    }

    private fun showClickedImage(link: String) {
        val action = FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentBigImage(link)
        findNavController().navigate(action)
    }

    // Watch trailers
    private fun getVideo(filmName: String) {
        binding.watchTrailersContainer.setOnClickListener {
            val action = FragmentFilmDetailDirections
                .actionFragmentFilmDetailToFragmentVideo(filmName)
            findNavController().navigate(action)
        }
    }

    // Similar films
    private fun setSimilarFilms() {
        similarAdapter = FilmAdapter(20, { showAllSimilarFilms() }, { onSimilarFilmClick(it) })

        binding.filmSimilarList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.filmSimilarList.adapter = similarAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFilmSimilar.collect {
                    similarAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countSimilarFilm.collect {
                    binding.filmSimilarCount.text = it.toString()
                }
            }
        }
        binding.apply {
            filmSimilarLabel.setOnClickListener { showAllSimilarFilms() }
            filmSimilarBtn.setOnClickListener { showAllSimilarFilms() }
            filmSimilarCount.setOnClickListener { showAllSimilarFilms() }
        }
    }

    private fun showAllSimilarFilms() {
        findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentSimilarFilms)
    }

    private fun onSimilarFilmClick(newFilmId: Int) {
        viewModel.putFilmId(newFilmId)
        val action =
            FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentFilmDetail(newFilmId)
        findNavController().navigate(action)
    }

    private fun getRatingName(film: ResponseCurrentFilm): String {
        val result = mutableListOf<String>()
        val rating = when {
            film.ratingImdb != null -> film.ratingImdb.toString()  //this is my priority
            film.ratingKinopoisk != null -> film.ratingKinopoisk.toString()
            film.ratingMpaa != null -> film.ratingMpaa.toString()
            else -> null
        }
        if (rating != null) result.add(rating)
        val name = when {
            film.nameOriginal != null -> film.nameOriginal  //this is my priority
            film.nameRu != null -> film.nameRu
            film.nameEn != null -> film.nameEn
            else -> null
        }
        if (name != null) result.add(name)
        return result.joinToString(", ")
    }

    private fun getName(film: ResponseCurrentFilm): String {
        return when {
            film.nameRu != null -> film.nameRu
            film.nameEn != null -> film.nameEn
            film.nameOriginal != null -> film.nameOriginal
            else -> ""
        }
    }

    private fun getYearAndGenres(film: ResponseCurrentFilm, context: Context): String {
        val result = mutableListOf<String>()

        if (film.type == TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)) {
            val tempStr = mutableListOf<String>()
            if (film.startYear != null && film.endYear != null) {
                tempStr.add(film.startYear.toString())
                tempStr.add(film.endYear.toString())
            } else {
                if (film.startYear != null) tempStr.add(film.startYear.toString())
                else context.getString(R.string.placeholder_series_start_year)
                if (film.endYear != null) tempStr.add(film.endYear.toString())
                else context.getString(R.string.placeholder_series_end_year)
            }
            result.add(tempStr.joinToString("-"))
        } else {
            if (film.year != null) result.add(film.year.toString())
        }

        if (film.genres.size > 1) {
            val genreNameList = mutableListOf<String>()
            repeat(2) {
                genreNameList.add(film.genres[it].genre)
            }
            result.add(genreNameList.joinToString(", "))
        } else if (film.genres.size == 1) {
            result.add(film.genres[0].genre)
        } else result.add("")

        return result.joinToString(", ")
    }

    private fun getStrCountriesLengthAge(film: ResponseCurrentFilm): String {
        val result = mutableListOf<String?>()
        result.add(film.getCountries())
        if (film.getLength() != null) result.add(film.getLength())
        if (film.getAgeLimit() != null) result.add("${film.getAgeLimit()}+")

        return result.joinToString(", ")
    }

    private fun ResponseCurrentFilm.getCountries(): String {
        return if (this.countries.size > 1) {
            val list = mutableListOf<String>()
            repeat(this.countries.size - 1) {
                list.add(this.countries[it].country)
            }
            list.joinToString(", ")
        } else if (this.countries.size == 1) {
            this.countries[0].country
        } else {
            ""
        }
    }

    private fun ResponseCurrentFilm.getLength(): String? {
        return if (this.filmLength != null) {
            val hours = this.filmLength.div(60)
            val minutes = this.filmLength.rem(60)
            "$hours ${getString(R.string.hour_short)} $minutes ${getString(R.string.minutes_short)}"
        } else null
    }

    private fun ResponseCurrentFilm.getAgeLimit(): String? {
        return if (this.ratingAgeLimits != null) this.ratingAgeLimits.removePrefix("age")
        else null
    }

    companion object {
        private const val MAX_ACTORS_COLUMN = 5
        private const val MAX_ACTORS_ROWS = 4
        private const val MAX_MAKERS_COLUMN = 3
        private const val MAX_MAKERS_ROWS = 2
        private const val INTENT_TYPE = "text/plain"
        private const val INTENT_TITLE = "Share Link"
        private const val POPUP_WINDOW_TAG = "POP_UP"
    }
}