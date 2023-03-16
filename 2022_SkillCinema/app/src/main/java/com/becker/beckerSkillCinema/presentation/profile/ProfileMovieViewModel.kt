package com.becker.beckerSkillCinema.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.becker.beckerSkillCinema.data.filmById.ResponseCurrentFilm
import com.becker.beckerSkillCinema.data.localData.entities.*
import com.becker.beckerSkillCinema.data.profile.Collections
import com.becker.beckerSkillCinema.domain.UseCaseLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProfileMovieViewModel @Inject constructor(
    private val useCaseLocal: UseCaseLocal,
    app: Application
) : AndroidViewModel(app) {

    private val _movieSelected = MutableStateFlow(0)
    val movieSelected = _movieSelected.asStateFlow()

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

    fun getAllInteresting() = useCaseLocal.getAllInterestingMovies()

    private suspend fun addMovieToInteresting(movieId: Int) {
        useCaseLocal.addMovieToInteresting(
            interesting = Interesting(
                interestingId = movieId,
                dateAdded = getDateTime()
            )
        )
    }

    private fun getDateTime(): String {
        val watchedDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(watchedDate).toString()
    }

    private suspend fun deleteAllMoviesFromInteresting() {
        useCaseLocal.cleanInteresting()
    }

    fun addCurrentFilmToHistory(movieId: Int) {
        viewModelScope.launch {
            try {
                addMovieToInteresting(movieId)
            } catch (e: Throwable) {
                Timber.e("addCurrentFilmToHistory $e")
            }
        }
    }

    fun onCleanInterestingClick() {
        viewModelScope.launch {
            try {
                deleteAllMoviesFromInteresting()
            } catch (e: Throwable) {
                Timber.e("onCleanInterestingClick $e")
            }
        }
    }

    fun buildInterestingList(allInteresting: List<Interesting>) {
        viewModelScope.launch {
            try {
                val interestingList = mutableListOf<Movie>()
                allInteresting.forEach { item ->
                    val interestingMovie = useCaseLocal.getMovieFromDataBaseById(item.interestingId)
                    interestingList.add(interestingMovie)
                }
                _interestingList.value = interestingList
            } catch (e: Throwable) {
                Timber.e("buildInterestingList $e")
            }
        }
    }

    fun getAllMoviesFromCustomCollection() = useCaseLocal.getAllMoviesFromCustomCollection()

    suspend fun addMovieToCustomCollection(collectionName: String, movieId: Int) {
        useCaseLocal.addMovieToCustomCollection(
            customCollection = CustomCollection(
                collectionName = collectionName,
                movieId = movieId,
                dateAdded = getDateTime()
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
            try {
                deleteCustomCollection(collectionName = collectionName)
            } catch (e: Throwable) {
                Timber.e("onDeleteCollectionButtonClick $e")
            }
        }
    }

    fun getCustomCollectionNames(allMovies: List<CustomCollection>) {
        viewModelScope.launch {
            try {
                val names = mutableListOf<String>()
                allMovies.forEach {
                    names.add(it.collectionName)
                }
                _customCollectionNamesList.value = names
            } catch (e: Throwable) {
                Timber.e("getCustomCollectionNames $e")
            }
        }
    }

    fun buildCustomCollectionList(allMovies: List<CustomCollection>) {
        viewModelScope.launch {
            try {
                val customCollectionList = mutableListOf<Movie>()
                _customCollectionChosen.collectLatest { customCollection ->
                    if (customCollection != null) {
                        val filteredList =
                            allMovies.filter { it.collectionName == customCollection.collectionName }
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
            } catch (e: Throwable) {
                Timber.e("buildCustomCollectionList $e")
            }
        }
    }

    fun getCustomCollections(allMovies: List<CustomCollection>) {
        val date = getDateTime()
        viewModelScope.launch {
            try {
                val filteredCollections = mutableListOf<CustomCollection>()
                val emptyMovies = mutableListOf<CustomCollection>()
                allMovies.forEach { if (it.movieId == 0) emptyMovies.add(it) }
                emptyMovies.forEach { itemEmptyMovie ->
                    val groupedCollections =
                        allMovies.filter { it.collectionName == itemEmptyMovie.collectionName }
                    if (groupedCollections.size > 1) {
                        deleteMovieFromCustomCollection(itemEmptyMovie.collectionName, 0)
                    }
                }
                val names = allMovies.groupBy { it.collectionName }
                names.forEach { (t, u) ->
                    if (u.contains(CustomCollection(t, 0, date))) {
                        filteredCollections.add(CustomCollection(t, u.size - 1, date))
                    } else filteredCollections.add(CustomCollection(t, u.size, date))
                }
                _customCollections.value = filteredCollections
            } catch (e: Throwable) {
                Timber.e("getCustomCollections $e")
            }
        }
    }

    fun onCustomCollectionClick(customCollection: CustomCollection) {
        viewModelScope.launch {
            try {
                _customCollectionChosen.value = customCollection
            } catch (e: Throwable) {
                Timber.e("onCustomCollectionClick $e")
            }
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
            try {
                val customCollectionChosen =
                    allMovies.filter { it.collectionName == collectionName }
                if (index <= collectionNumber - 1) {
                    val initialStatus = _addedToCustomCollection.value.entries
                    val status = mutableMapOf<String, Boolean>()
                    initialStatus.forEach { status[it.key] = it.value }
                    status[collectionName] = !customCollectionChosen.all { it.movieId != movieId }
                    _addedToCustomCollection.value = status
                }
            } catch (e: Throwable) {
                Timber.e("checkCustomCollection $e")
            }
        }
    }

    fun onCustomCollectionButtonClick(collectionName: String, movieId: Int) {
        viewModelScope.launch {
            try {
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
            } catch (e: Throwable) {
                Timber.e("onCustomCollectionButtonClick $e")
            }
        }
    }

    fun getMovieFromDataBaseById(movieId: Int) {
        viewModelScope.launch {
            try {
                _movieById.value = useCaseLocal.getMovieFromDataBaseById(movieId)
            } catch (e: Throwable) {
                Timber.e("getMovieFromDataBaseById $e")
            }
        }
    }

    fun addMovieToDataBase(movie: ResponseCurrentFilm) {
        viewModelScope.launch {
            try {
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
            } catch (e: Throwable) {
                Timber.e("addMovieToDataBase $e")
            }
        }
    }

    fun getAllWatched() = useCaseLocal.getAllWatched()

    private suspend fun addToWatched(movieId: Int) {
        viewModelScope.launch {
            try {
                useCaseLocal.addToWatched(
                    watched = Watched(
                        watchedId = movieId,
                        dateAdded = getDateTime()
                    )
                )
            } catch (e: Throwable) {
                Timber.e("addToWatched $e")
            }
        }
    }

    private suspend fun deleteFromWatched(movieId: Int) {
        viewModelScope.launch {
            try {
                useCaseLocal.deleteFromWatched(movieId)
            } catch (e: Throwable) {
                Timber.e("deleteFromWatched $e")
            }
        }
    }

    fun checkWatched(movieId: Int, allWatched: List<Watched>) {
        viewModelScope.launch {
            try {
                _addedToWatched.value = !allWatched.all { it.watchedId != movieId }
            } catch (e: Throwable) {
                Timber.e("checkWatched $e")
            }
        }
    }

    fun onWatchedButtonClick(movieId: Int) {
        viewModelScope.launch {
            try {
                if (!_addedToWatched.value) {
                    addToWatched(movieId)
                    _addedToWatched.value = true
                    _addedToWatch.value = false
                    deleteFromToWatch(movieId)
                } else {
                    deleteFromWatched(movieId)
                    _addedToWatched.value = false
                }
            } catch (e: Throwable) {
                Timber.e("onWatchedButtonClick $e")
            }
        }
    }

    fun buildWatchedList(allWatched: List<Watched>) {
        viewModelScope.launch {
            try {
                val watchedList = mutableListOf<Movie>()
                allWatched.forEach { item ->
                    val watchedMovie = useCaseLocal.getMovieFromDataBaseById(item.watchedId)
                    watchedList.add(watchedMovie)
                }
                _watchedList.value = watchedList
            } catch (e: Throwable) {
                Timber.e("buildWatchedList $e")
            }
        }
    }

    private suspend fun deleteAllMoviesFromWatched() {
        useCaseLocal.cleanWatched()
    }

    fun onCleanWatchedClick() {
        viewModelScope.launch {
            try {
                deleteAllMoviesFromWatched()
            } catch (e: Throwable) {
                Timber.e("onCleanWatchedClick $e")
            }
        }
    }

    fun getAllFavorites() = useCaseLocal.getAllFavorites()

    private suspend fun addToFavorites(movieId: Int) {
        viewModelScope.launch {
            try {
                useCaseLocal.addToFavorites(
                    favorites = Favorites(
                        favoritesId = movieId,
                        dateAdded = getDateTime()
                    )
                )
            } catch (e: Throwable) {
                Timber.e("addToFavorites $e")
            }
        }
    }

    private suspend fun deleteFromFavorites(movieId: Int) {
        viewModelScope.launch {
            try {
                useCaseLocal.deleteFromFavorites(movieId)
            } catch (e: Throwable) {
                Timber.e("deleteFromFavorites $e")
            }
        }
    }

    fun checkFavorites(movieId: Int, allFavorites: List<Favorites>) {
        viewModelScope.launch {
            try {
                _addedToFavorites.value = !allFavorites.all { it.favoritesId != movieId }
            } catch (e: Throwable) {
                Timber.e("checkFavorites $e")
            }
        }
    }

    fun onFavoritesButtonClick(movieId: Int) {
        viewModelScope.launch {
            try {
                if (!_addedToFavorites.value) {
                    addToFavorites(movieId)
                    _addedToFavorites.value = true
                } else {
                    deleteFromFavorites(movieId)
                    _addedToFavorites.value = false
                }
            } catch (e: Throwable) {
                Timber.e("onFavoritesButtonClick $e")
            }
        }
    }

    fun buildFavoritesList(allFavorites: List<Favorites>) {
        viewModelScope.launch {
            try {
                val favoritesList = mutableListOf<Movie>()
                allFavorites.forEach { item ->
                    val favoriteMovie = useCaseLocal.getMovieFromDataBaseById(item.favoritesId)
                    favoritesList.add(favoriteMovie)
                }
                _favoritesList.value = favoritesList
            } catch (e: Throwable) {
                Timber.e("buildFavoritesList $e")
            }
        }
    }

    fun getAllToWatch(): Flow<List<ToWatch>> = useCaseLocal.getAllToWatch()

    private suspend fun addToWatch(movieId: Int) {
        viewModelScope.launch {
            try {
                useCaseLocal.addToWatch(
                    toWatch = ToWatch(
                        toWatchId = movieId,
                        dateAdded = getDateTime()
                    )
                )
            } catch (e: Throwable) {
                Timber.e("addToWatch $e")
            }
        }
    }

    private suspend fun deleteFromToWatch(movieId: Int) {
        viewModelScope.launch {
            try {
                useCaseLocal.deleteFromToWatch(movieId)
            } catch (e: Throwable) {
                Timber.e("deleteFromToWatch $e")
            }
        }
    }

    fun checkToWatch(movieId: Int, allToWatch: List<ToWatch>) {
        viewModelScope.launch {
            try {
                _addedToWatch.value = !allToWatch.all { it.toWatchId != movieId }
            } catch (e: Throwable) {
                Timber.e("checkToWatch $e")
            }
        }
    }

    fun onToWatchButtonClick(movieId: Int) {
        viewModelScope.launch {
            try {
                if (!_addedToWatch.value) {
                    addToWatch(movieId)
                    _addedToWatch.value = true
                } else {
                    deleteFromToWatch(movieId)
                    _addedToWatch.value = false
                }
            } catch (e: Throwable) {
                Timber.e("onToWatchButtonClick $e")
            }
        }
    }

    fun buildToWatchList(allToWatch: List<ToWatch>) {
        viewModelScope.launch {
            try {
                val toWatchList = mutableListOf<Movie>()
                allToWatch.forEach { item ->
                    val toWatchMovie = useCaseLocal.getMovieFromDataBaseById(item.toWatchId)
                    toWatchList.add(toWatchMovie)
                }
                _toWatchList.value = toWatchList
            } catch (e: Throwable) {
                Timber.e("buildToWatchList $e")
            }
        }
    }

    fun chooseCollection(collections: Collections) {
        viewModelScope.launch {
            try {
                _collectionChosen.value = collections
            } catch (e: Throwable) {
                Timber.e("chooseCollection $e")
            }
        }
    }

    fun movieSelected(itemId: Int) {
        viewModelScope.launch {
            try {
                _movieSelected.value = itemId
            } catch (e: Throwable) {
                Timber.e("movieSelected $e")
            }
        }
    }
}