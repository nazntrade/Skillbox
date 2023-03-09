package com.becker.beckerSkillCinema.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.localData.entities.*
import com.becker.beckerSkillCinema.data.profile.Collections
import com.becker.beckerSkillCinema.domain.UseCaseLocal
import com.becker.beckerSkillCinema.presentation.StateLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMovieViewModel @Inject constructor(
    private val useCaseLocal: UseCaseLocal,
    app: Application
) : AndroidViewModel(app) {

    // Variables

    private val _loadingState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadingState = _loadingState.asStateFlow()

    private val _movieSelected = MutableStateFlow(0)
    val movieSelected = _movieSelected.asStateFlow()

    private val _movieSelectedName = MutableStateFlow("")
    val movieSelectedName = _movieSelectedName.asStateFlow()

    private val _movieInfo = MutableStateFlow<ResponseCurrentFilm?>(null)
    val movieInfo = _movieInfo.asStateFlow()

    private val _addedToFavorites = MutableStateFlow(false)
    val addedToFavorites = _addedToFavorites.asStateFlow()

    private val _addedToWatch = MutableStateFlow(false)
    val addedToWatch = _addedToWatch.asStateFlow()

    private val _addedToWatched = MutableStateFlow(false)
    val addedToWatched = _addedToWatched.asStateFlow()

    private val _movieById = MutableStateFlow<Movie?>(null)
    val movieById = _movieById.asStateFlow()

    private val _addedToCustomCollection = MutableStateFlow(emptyMap<String, Boolean>())
    val addedToCustomCollection = _addedToCustomCollection.asStateFlow()

    private val _customCollectionNamesList = MutableStateFlow<List<String>>(emptyList())
    val customCollectionNamesList = _customCollectionNamesList

    private val _interestingList = MutableStateFlow<List<Movie>>(emptyList())
    val interestingList = _interestingList.asStateFlow()

    private val _watchedList = MutableStateFlow<List<Movie>>(emptyList())
    val watchedList = _watchedList.asStateFlow()

    private val _watchedMovies = MutableStateFlow<List<Watched>>(emptyList())

    private val _favoritesList = MutableStateFlow<List<Movie>>(emptyList())
    val favoritesList = _favoritesList.asStateFlow()

    private val _toWatchList = MutableStateFlow<List<Movie>>(emptyList())
    val toWatchList = _toWatchList.asStateFlow()

    private val _customCollectionList = MutableStateFlow<List<Movie>>(emptyList())
    val customCollectionList = _customCollectionList.asStateFlow()

    private val _collectionChosen = MutableStateFlow<Collections>(Collections.Favorites)
    val collectionChosen = _collectionChosen.asStateFlow()

    private val _customCollectionChosen = MutableStateFlow<CustomCollection?>(null)
    val customCollectionChosen = _customCollectionChosen.asStateFlow()

    private val _customCollections = MutableStateFlow<List<CustomCollection>>(emptyList())
    val customCollections = _customCollections.asStateFlow()

    private val _showWatchedAtSearchResult = MutableStateFlow(true)
    val showWatchedAtSearchResult = _showWatchedAtSearchResult.asStateFlow()

//DataBaseQueries

    fun getAllInteresting() = useCaseLocal.getAllInterestingMovies()

    private suspend fun addMovieToInteresting(movieId: Int) {
        useCaseLocal.addMovieToInteresting(
            interesting = Interesting(
                interestingId = movieId
            )
        )
    }

    private suspend fun deleteAllMoviesFromInteresting() {
        useCaseLocal.cleanInteresting()
    }

    fun onInterestingButtonClick(movieId: Int) {
        viewModelScope.launch {
            addMovieToInteresting(movieId)
        }
    }

    fun onCleanInterestingClick() {
        viewModelScope.launch {
            deleteAllMoviesFromInteresting()
        }
    }

    fun buildInterestingList(allInteresting: List<Interesting>) {
        viewModelScope.launch {
            val interestingList = mutableListOf<Movie>()
            allInteresting.forEach { item ->
                val interestingMovie = useCaseLocal.getMovieFromDataBaseById(item.interestingId)
                interestingList.add(interestingMovie)
            }
            _interestingList.value = interestingList
        }
    }

    fun getAllMoviesFromCustomCollection() = useCaseLocal.getAllMoviesFromCustomCollection()

    suspend fun addMovieToCustomCollection(collectionName: String, movieId: Int) {
        useCaseLocal.addMovieToCustomCollection(
            customCollection = CustomCollection(
                collectionName = collectionName, movieId = movieId
            )
        )
    }

    private suspend fun deleteMovieFromCustomCollection(collectionName: String, movieId: Int) {
        useCaseLocal.deleteMovieFromCustomCollection(collectionName, movieId)
    }

    private suspend fun deleteCustomCollection(collectionName: String) {
        useCaseLocal.deleteCustomCollection(collectionName)
    }

    fun onDeleteCollectionButtonClick(collectionName: String) {
        viewModelScope.launch {
            deleteCustomCollection(collectionName = collectionName)
        }
    }

    fun getCustomCollectionNames(allMovies: List<CustomCollection>) {
        viewModelScope.launch {
            val names = mutableListOf<String>()
            allMovies.forEach {
                names.add(it.collectionName)
            }
            _customCollectionNamesList.value = names
        }
    }

    fun buildCustomCollectionList(allMovies: List<CustomCollection>) {
        viewModelScope.launch {
            val customCollectionList = mutableListOf<Movie>()
            _customCollectionChosen.collectLatest {
                val chosenCollection = it
                if (chosenCollection != null) {
                    val filteredList =
                        allMovies.filter { it.collectionName == chosenCollection.collectionName }
                    filteredList.forEach { item ->
                        if (item.movieId != 0) {
                            val customCollectionMovie =
                                useCaseLocal.getMovieFromDataBaseById(item.movieId)
                            customCollectionList.add(customCollectionMovie)
                        }
                    }
                }
                _customCollectionList.value = customCollectionList
            }
        }
    }

    fun getCustomCollections(allMovies: List<CustomCollection>) {
        viewModelScope.launch {
            val filteredCollections = mutableListOf<CustomCollection>()
            val emptyMovie = mutableListOf<CustomCollection>()
            allMovies.forEach { if (it.movieId == 0) emptyMovie.add(it) }
            emptyMovie.forEach { emptyCollection ->
                val groupedCollections =
                    allMovies.filter { it.collectionName == emptyCollection.collectionName }
                if (groupedCollections.size > 1) {
                    deleteMovieFromCustomCollection(emptyCollection.collectionName, 0)
                }
            }
            val names = allMovies.groupBy { it.collectionName }
            names.forEach { (t, u) ->
                if (u.contains(CustomCollection(t, 0))) {
                    filteredCollections.add(CustomCollection(t, u.size - 1))
                } else filteredCollections.add(CustomCollection(t, u.size))
            }
            _customCollections.value = filteredCollections
        }
    }

    fun onCustomCollectionClick(customCollection: CustomCollection) {
        viewModelScope.launch {
            _customCollectionChosen.value = customCollection
        }
    }

    fun checkCustomCollection(
        collectionName: String,
        movieId: Int,
        index: Int,
        collectionNumber: Int,
        allMovies: List<CustomCollection>
    ) {
        viewModelScope.launch {
            val customCollectionChosen = allMovies.filter { it.collectionName == collectionName }
            if (index <= collectionNumber - 1) {
                val initialStatus = _addedToCustomCollection.value.entries
                val status = mutableMapOf<String, Boolean>()
                initialStatus.forEach { status[it.key] = it.value }
                status[collectionName] = !customCollectionChosen.all { it.movieId != movieId }
                _addedToCustomCollection.value = status
            }
        }
    }

    fun onCustomCollectionButtonClick(collectionName: String, movieId: Int) {
        viewModelScope.launch {
            if (_addedToCustomCollection.value[collectionName] == false) {
                val initialStatus = _addedToCustomCollection.value.entries
                val status = mutableMapOf<String, Boolean>()
                initialStatus.forEach { status[it.key] = it.value }
                status[collectionName] = true
                _addedToCustomCollection.value = status
                addMovieToCustomCollection(collectionName, movieId)
            } else {
                val initialStatus = _addedToCustomCollection.value.entries
                val status = mutableMapOf<String, Boolean>()
                initialStatus.forEach { status[it.key] = it.value }
                status[collectionName] = false
                _addedToCustomCollection.value = status
                deleteMovieFromCustomCollection(collectionName, movieId)
            }
        }
    }

    private fun getAllMoviesFromDataBase() = useCaseLocal.getAllMovies()

    fun getMovieFromDataBaseById(movieId: Int) {
        viewModelScope.launch {
            _movieById.value = useCaseLocal.getMovieFromDataBaseById(movieId)
        }
    }

    fun addMovieToDataBase(
        movie: ResponseCurrentFilm
    ) {
        viewModelScope.launch {
            useCaseLocal.addMovie(
                movie = Movie(
                    movieId = movie.kinopoiskId,
                    posterUri = movie.posterUrl,
                    rating = movie.ratingKinopoisk ?: movie.ratingImdb
                    ?: movie.ratingFilmCritics ?: movie.ratingRfCritics,
                    genre = movie.genres.firstOrNull()?.genre,
                    movieName = movie.nameRu ?: movie.nameEn ?: movie.nameOriginal,
                    country = movie.countries.firstOrNull()?.country,
                    logoUrl = movie.logoUrl,
                    description = movie.description,
                    shortDescription = movie.shortDescription,
                    filmLength = movie.filmLength,
                    imdbId = movie.imdbId,
                    nameEn = movie.nameEn ?: movie.nameOriginal,
                    ratingAgeLimits = movie.ratingAgeLimits,
                    serial = movie.serial,
                    shortFilm = movie.shortFilm,
                    year = movie.year
                )
            )
        }
    }


//    private suspend fun addMovieToDataBase(
//        movieId: Int
//    ) {
//        viewModelScope.launch {
//            _movieInfo.collectLatest {
//                val info = it
//                if (info != null && info.kinopoiskId == movieId) {
//                    useCaseLocal.addMovie(
//                        movie = Movie(
//                            movieId = movieId,
//                            posterUri = info.posterUrl,
//                            rating = info.ratingKinopoisk ?: info.ratingImdb
//                            ?: info.ratingFilmCritics ?: info.ratingRfCritics,
//                            genre = info.genres.firstOrNull()?.genre,
//                            movieName = info.nameRu ?: info.nameEn ?: info.nameOriginal,
//                            country = info.countries.firstOrNull()?.country,
//                            logoUrl = info.logoUrl,
//                            description = info.description,
//                            shortDescription = info.shortDescription,
//                            filmLength = info.filmLength,
//                            imdbId = info.imdbId,
//                            nameEn = info.nameEn ?: info.nameOriginal,
//                            ratingAgeLimits = info.ratingAgeLimits,
//                            serial = info.serial,
//                            shortFilm = info.shortFilm,
//                            year = info.year
//                        )
//                    )
//                }
//            }
//        }
//    }
//
//    fun onAddMovieToDataBase(movieId: Int) {
//        viewModelScope.launch {
//            addMovieToDataBase(movieId)
//        }
//    }

    fun getAllWatched() = useCaseLocal
        .getAllWatched()

    private suspend fun addToWatched(movieId: Int) {
        viewModelScope.launch {
            _loadingState.value = StateLoading.Loading
            useCaseLocal.addToWatched(
                watched = Watched(
                    watchedId = movieId
                )
            )
            _loadingState.value = StateLoading.Success
        }
    }

    private suspend fun deleteFromWatched(movieId: Int) {
        viewModelScope.launch {
            useCaseLocal.deleteFromWatched(movieId)
        }
    }

    fun checkWatched(movieId: Int, allWatched: List<Watched>) {
        viewModelScope.launch {
            _addedToWatched.value = !allWatched.all { it.watchedId != movieId }
        }
    }

    fun onWatchedButtonClick(movieId: Int) {
        viewModelScope.launch {
            if (!_addedToWatched.value) {
                addToWatched(movieId)
                _addedToWatched.value = true
                _addedToWatch.value = false
                deleteFromToWatch(movieId)
            } else {
                deleteFromWatched(movieId)
                _addedToWatched.value = false
            }
        }
    }

    fun buildWatchedList(allWatched: List<Watched>) {
        viewModelScope.launch {
            val watchedList = mutableListOf<Movie>()
            allWatched.forEach { item ->
                val watchedMovie = useCaseLocal.getMovieFromDataBaseById(item.watchedId)
                watchedList.add(watchedMovie)
            }
            _watchedList.value = watchedList
        }
    }

    fun getWatchedMovies(allWatched: List<Watched>) {
        viewModelScope.launch {
            _watchedMovies.value = allWatched
        }
    }

    private suspend fun deleteAllMoviesFromWatched() {
        useCaseLocal.cleanWatched()
    }

    fun onCleanWatchedClick() {
        viewModelScope.launch {
            deleteAllMoviesFromWatched()
        }
    }

    fun getAllFavorites() = useCaseLocal.getAllFavorites()

    private suspend fun addToFavorites(movieId: Int) {
        viewModelScope.launch {
            useCaseLocal.addToFavorites(
                favorites = Favorites(
                    favoritesId = movieId
                )
            )
        }
    }

    private suspend fun deleteFromFavorites(movieId: Int) {
        viewModelScope.launch {
            useCaseLocal.deleteFromFavorites(movieId)
        }
    }

    fun checkFavorites(movieId: Int, allFavorites: List<Favorites>) {
        viewModelScope.launch {
            _addedToFavorites.value = !allFavorites.all { it.favoritesId != movieId }
        }
    }

    fun onFavoritesButtonClick(movieId: Int) {
        viewModelScope.launch {
            if (!_addedToFavorites.value) {
                addToFavorites(movieId)
                _addedToFavorites.value = true
            } else {
                deleteFromFavorites(movieId)
                _addedToFavorites.value = false
            }
        }
    }

    fun buildFavoritesList(allFavorites: List<Favorites>) {
        viewModelScope.launch {
            val favoritesList = mutableListOf<Movie>()
            allFavorites.forEach { item ->
                val favoriteMovie = useCaseLocal.getMovieFromDataBaseById(item.favoritesId)
                favoritesList.add(favoriteMovie)
            }
            _favoritesList.value = favoritesList
        }
    }

    fun getAllToWatch(): Flow<List<ToWatch>> = useCaseLocal.getAllToWatch()

    private suspend fun addToWatch(movieId: Int) {
        viewModelScope.launch {
            useCaseLocal.addToWatch(
                toWatch = ToWatch(
                    toWatchId = movieId
                )
            )
        }
    }

    private suspend fun deleteFromToWatch(movieId: Int) {
        viewModelScope.launch {
            useCaseLocal.deleteFromToWatch(movieId)
        }
    }

    fun checkToWatch(movieId: Int, allToWatch: List<ToWatch>) {
        viewModelScope.launch {
            _addedToWatch.value = !allToWatch.all { it.toWatchId != movieId }
        }
    }

    fun onToWatchButtonClick(movieId: Int) {
        viewModelScope.launch {
            if (!_addedToWatch.value) {
                addToWatch(movieId)
                _addedToWatch.value = true
            } else {
                deleteFromToWatch(movieId)
                _addedToWatch.value = false
            }
        }
    }

    fun buildToWatchList(allToWatch: List<ToWatch>) {
        viewModelScope.launch {
            val toWatchList = mutableListOf<Movie>()
            allToWatch.forEach { item ->
                val toWatchMovie = useCaseLocal.getMovieFromDataBaseById(item.toWatchId)
                toWatchList.add(toWatchMovie)
            }
            _toWatchList.value = toWatchList

        }
    }

    fun showWatchedMoviesAtSearchResult() {
        viewModelScope.launch {
            _showWatchedAtSearchResult.value = !_showWatchedAtSearchResult.value
        }
    }

    fun chooseCollection(collections: Collections) {
        viewModelScope.launch {
            _collectionChosen.value = collections
        }
    }

    fun movieSelected(itemId: Int) {
        viewModelScope.launch {
            _movieSelected.value = itemId
        }
    }
}