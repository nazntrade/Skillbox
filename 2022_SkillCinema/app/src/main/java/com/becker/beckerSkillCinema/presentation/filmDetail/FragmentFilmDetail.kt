package com.becker.beckerSkillCinema.presentation.filmDetail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
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
import com.becker.beckerSkillCinema.data.TOP_TYPES
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import com.becker.beckerSkillCinema.databinding.FragmentFilmDetailBinding
import com.becker.beckerSkillCinema.presentation.StateLoading
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment
import com.becker.beckerSkillCinema.presentation.home.adapters.filmAdapter.FilmAdapter
import com.becker.beckerSkillCinema.utils.autoCleared
import kotlinx.coroutines.launch
import com.becker.beckerSkillCinema.presentation.filmDetail.galleryAdapter.GalleryAdapter
import com.becker.beckerSkillCinema.presentation.filmDetail.staffAdapter.StaffAdapter
import com.becker.beckerSkillCinema.utils.loadImage
import timber.log.Timber

class FragmentFilmDetail :
    ViewBindingFragment<FragmentFilmDetailBinding>(FragmentFilmDetailBinding::inflate) {

    // https://slack-chats.kotlinlang.org/t/471784/can-anyone-explain-what-is-by-activityviewmodels-by-fragment
    private val viewModel: FilmDetailViewModel by activityViewModels()

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

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        stateLoadingListener()              // Set listener downloads

        setFilmDetails()                    // Set poster with info on it
        setFilmActors()                     // Ser ListActors
        setFilmCrew()                       // Set ListFilmCrew
        setFilmGallery()                    // Set Gallery
        setSimilarFilms()                   // Set List Similar Films
    }

    private fun stateLoadingListener() {

        binding.progressGroupContainer.loadingRefreshBtn
            .setOnClickListener { viewModel.getFilmById() }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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

    // About film
    private fun setFilmDetails() {
        if (incomeArgs.filmId != viewModel.currentFilmId) {
            viewModel.getFilmId()
            viewModel.getFilmById()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    try {
                        viewModel.currentFilm.collect { film ->
                            if (film != null) {
                                if (film.type == TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)) {
                                    binding.seasonsGroup.isVisible = true
                                    viewModel.getSeasons(film.kinopoiskId)
                                    getSeriesSeasons(getName(film))
                                } else {
                                    binding.seasonsGroup.isVisible = false
                                }
                                binding.apply {
                                    filmName.text = getName(film)
                                    filmPoster.loadImage(film.posterUrl)
                                    filmDescriptionShort.text = film.shortDescription
                                    filmDescriptionFull.text = film.description
                                    filmRatingNameTv.text = getRatingName(film)
                                    filmYearGenresTv.text = getYearAndGenres(film, requireContext())
                                    filmCountryLengthAgeLimitTv.text =
                                        getStrCountriesLengthAge(film)
                                }
                            }
                        }
                    } catch (e: Throwable) {
                        Timber.e("setFilmDetails $e")
                    }
                }
            }
        }
    }

    // About season and series
    private fun getSeriesSeasons(seriesName: String) {
        binding.seriesSeasonsBtn.setOnClickListener { showAllSeasons(seriesName) }
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewModel.seasons.collect { seasons ->
                    val seasonsCount = seasons.size
                    var seriesCount = 0

                    seasons.forEach { season ->
                        seriesCount += season.episodes.size
                    }

                    val seasonStr =
                        resources.getQuantityString(
                            R.plurals.film_details_series_count,
                            seasonsCount,
                            seasonsCount
                        )
                    val episodeStr =
                        resources.getQuantityString(
                            R.plurals.film_details_episode_count,
                            seriesCount,
                            seriesCount
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

    private fun showAllSeasons(seriesName: String) {    //////////////   move to
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmActors.collect { actorList ->
                binding.filmActorsCount.text = actorList.size.toString()
                if (actorList.size < MAX_ACTORS_COLUMN * MAX_ACTORS_ROWS) {
                    actorAdapter.submitList(actorList)
                } else {
                    val actorsListTemp = mutableListOf<ResponseStaffByFilmId>()
                    repeat(MAX_ACTORS_COLUMN * MAX_ACTORS_ROWS) { actorsListTemp.add(actorList[it]) }
                    actorAdapter.submitList(actorsListTemp)
                }
            }
        }
        binding.filmActorsBtn.setOnClickListener { showAllStaffs("ACTOR") }
        binding.filmActorsCount.setOnClickListener { showAllStaffs("ACTOR") }
    }

    private fun setFilmCrew() {
        makersAdapter = StaffAdapter { onStaffClick(it) }
        binding.filmMakersList.layoutManager =
            GridLayoutManager(
                requireContext(), MAX_MAKERS_ROWS, GridLayoutManager.HORIZONTAL, false
            )
        binding.filmMakersList.adapter = makersAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmMakers.collect { makersList ->
                if (makersList.size < MAX_MAKERS_COLUMN * MAX_MAKERS_ROWS) {
                    binding.filmMakersCount.isVisible = false
                    binding.filmMakersBtn.isVisible = false
                    makersAdapter.submitList(makersList)
                } else {
                    binding.filmMakersCount.isVisible = true
                    binding.filmMakersBtn.isVisible = true
                    binding.filmMakersCount.text = makersList.size.toString()
                    val makersListTemp = mutableListOf<ResponseStaffByFilmId>()
                    repeat(MAX_MAKERS_COLUMN * MAX_MAKERS_ROWS) { makersListTemp.add(makersList[it]) }
                    makersAdapter.submitList(makersListTemp)
                }
            }
        }
        binding.filmMakersBtn.setOnClickListener { showAllStaffs("") }
        binding.filmMakersCount.setOnClickListener { showAllStaffs("") }
    }

    private fun onStaffClick(staff: ResponseStaffByFilmId) {     //////////////   move to
        val action =
            FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentStaffDetail(staff.staffId)
        findNavController().navigate(action)
    }

    private fun showAllStaffs(professionalKey: String) {     //////////////   move to
        val action = FragmentFilmDetailDirections
            .actionFragmentFilmDetailToFragmentAllStaffsByFilm(professionalKey)
        findNavController().navigate(action)
    }

    // Gallery
    private fun setFilmGallery() {
        //
        binding.filmGalleryCount.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentGallery)
        }
        binding.filmGalleryBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentGallery)
        }

        galleryAdapter = GalleryAdapter(20, { showAllImages() }, { showClickedImage(it) })
        binding.filmGalleryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filmGalleryList.adapter = galleryAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmGallery.collect { responseGallery ->
                galleryAdapter.submitList(responseGallery)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.galleryTotalNumber.collect {
                binding.filmGalleryCount.text = it.toString()
            }
        }
    }

    private fun showAllImages() {    //////////////   move to
        findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentGallery)
    }

    private fun showClickedImage(link: String) {     ////////   expand
        ///////////////////
    }

    // Similar films
    private fun setSimilarFilms() {
        similarAdapter = FilmAdapter(20, { showAllSimilarFilms() }, { onSimilarFilmClick(it) })
        binding.filmSimilarList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filmSimilarList.adapter = similarAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmSimilar.collect {
                similarAdapter.submitList(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.countSimilarFilm.collect {
                binding.filmSimilarCount.text = it.toString()
            }
        }
        binding.filmSimilarBtn.setOnClickListener { showAllSimilarFilms() }
    }

    private fun onSimilarFilmClick(newFilmId: Int) {
        viewModel.putFilmId(newFilmId)
        viewModel.getFilmId()
        viewModel.getFilmById()

        binding.myScroll.scrollTo(0, 0)
        binding.filmDetailMotionLayout.jumpToState(R.id.expanded)
    }

    private fun showAllSimilarFilms() {    //////////////////////   move to
//////////////// to post already existed list similar films
        findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentSimilarFilms)
    }

    companion object {
        private const val MAX_ACTORS_COLUMN = 5
        private const val MAX_ACTORS_ROWS = 4
        private const val MAX_MAKERS_COLUMN = 3
        private const val MAX_MAKERS_ROWS = 2

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
                "$hours ч $minutes мин"
            } else null
        }

        private fun ResponseCurrentFilm.getAgeLimit(): String? {
            return if (this.ratingAgeLimits != null) this.ratingAgeLimits.removePrefix("age")
            else null
        }
    }
}