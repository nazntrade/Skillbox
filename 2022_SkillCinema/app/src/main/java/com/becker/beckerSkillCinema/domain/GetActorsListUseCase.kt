package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.CinemaRepository
import com.becker.beckerSkillCinema.data.staffByFilmId.ResponseStaffByFilmId
import javax.inject.Inject

class GetActorsListUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeActorsList(filmId: Int): List<ResponseStaffByFilmId> {
        return repository.getActorsByFilmId(filmId)
    }
}