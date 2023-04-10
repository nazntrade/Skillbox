package com.becker.beckerSkillCinema.domain

import com.becker.beckerSkillCinema.data.repositories.CinemaRepository
import com.becker.beckerSkillCinema.data.staffById.ResponseStaffById
import javax.inject.Inject

class GetStaffByIdUseCase @Inject constructor(
    private val repository: CinemaRepository
) {
    suspend fun executeStaffById(staffId: Int): ResponseStaffById {
        return repository.getStaffById(staffId)
    }
}