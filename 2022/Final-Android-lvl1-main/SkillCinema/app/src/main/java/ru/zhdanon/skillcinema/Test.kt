package ru.zhdanon.skillcinema

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.zhdanon.skillcinema.data.CinemaRepository

fun main() {
    val repository = CinemaRepository()
    runBlocking {
        launch(Dispatchers.IO) {
            val staff = repository.getStaffById(9144)

            staff.apply {
                println("1 - $personId | $webUrl | $nameRu | $nameEn | $sex")
                println("2 - $posterUrl | $growth")
                println("3 - $birthday | $death | $age | $birthPlace | $deathPlace")
                println("4 - $hasAwards | $profession")
                println("5 - ${facts.joinToString(" | ")}")
                println("6 - ${spouses?.joinToString(" | ")}")
                println("7 - ${films?.map { film -> film.nameRu }?.joinToString(" | ")}")
            }
        }
    }
}

//            val film = listOf(
//                repository.getFilmById(329),
//                repository.getFilmById(4477075),
//                repository.getFilmById(5116673),
//                repository.getFilmById(1297211),
//                repository.getFilmById(4639557),
//                repository.getFilmById(1405508)
//            )