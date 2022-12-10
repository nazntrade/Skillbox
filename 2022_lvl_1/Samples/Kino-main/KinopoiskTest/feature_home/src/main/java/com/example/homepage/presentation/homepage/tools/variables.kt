package com.example.homepage.presentation.homepage.tools

import com.example.core.tools.all.Month
import com.example.homepage.presentation.homepage.data.films.dto.CountryAndGenreDTO

import kotlin.random.Random
import kotlin.random.nextInt

fun CountryAndGenreDTO.getRandomCounter()=
    countries[Random.nextInt(0..countries.lastIndex)]

 fun CountryAndGenreDTO.getRandomGenre()=
    genres[Random.nextInt(0..genres.lastIndex)]


fun Int.converterInMonth(): String {
    var textMonth = ""
    if (this <= 0 || this > 12)
        textMonth = Month.AUGUST.name
    else
        Month.values().forEach { month ->
            if (this == month.count) textMonth = month.name
        }
    return textMonth
}