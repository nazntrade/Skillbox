package ru.zhdanon.skillcinema.domain

import ru.zhdanon.skillcinema.data.CinemaRepository
import ru.zhdanon.skillcinema.entity.HomeItem
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(private val cinemaRepository: CinemaRepository) {

    suspend fun executeTopFilms(topType: String, page: Int): List<HomeItem> {
        return cinemaRepository.getFilmsTop(topType, page)
    }
}