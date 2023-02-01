package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.CinemaRepository
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(
    private val repository: CinemaRepository
) {

    suspend fun executeVideoByFilmId(filmId: Int) =
        repository.getVideoByFilmId(filmId)

}