package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.repositories.CinemaRepository
import com.becker.beckerSkillCinema.entity.HomeItem
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(
    private val cinemaRepository: CinemaRepository
) {
    suspend fun executeTopFilms(topType: String, page: Int): List<HomeItem> {
        return cinemaRepository.getFilmsTop(topType, page)
    }
}