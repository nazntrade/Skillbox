package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.repositories.CinemaRepository
import com.becker.beckerSkillCinema.data.personFromSearch.ResponsePeopleFromSearch
import javax.inject.Inject

class GetPeopleFromSearchUseCase @Inject constructor(
    private val repository: CinemaRepository
) {
    suspend fun executePeopleFromSearch(name: String, page: Int): ResponsePeopleFromSearch {
        return repository.getPeopleFromSearch(name, page)
    }
}