package com.example.feature_database.repository

import com.example.core.tools.general.Country
import com.example.core.tools.general.Genre
import com.example.feature_database.Dao.CountryDao
import com.example.feature_database.Dao.FilmsDao
import com.example.feature_database.Dao.GenreDao

class DataBaseRepository(
    private val genreDao: GenreDao,
    private val countryDao: CountryDao,
    private val filmsDao: FilmsDao
) {

    fun insertGenre(genre:List <Genre>) = genreDao.insertGenre(genre)

    fun getAllGenre() = genreDao.getAllGenre()

    fun getAllCounter() = countryDao.getAllCounter()

    fun insertCounter(country: List<Country>) = countryDao.insertCounty(country)

    fun getStatusFilmList(filmsID:List<Int>) = filmsDao.getFilmStatusByListId(filmsID)

    fun getGenreNameByID(id:Int) = genreDao.getFromGenreID(id)

    fun genCountryNameById(id:Int) = countryDao.getFromCounterID(id)
}