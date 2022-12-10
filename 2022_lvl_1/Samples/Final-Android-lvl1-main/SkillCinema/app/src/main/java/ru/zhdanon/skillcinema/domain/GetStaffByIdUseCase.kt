package ru.zhdanon.skillcinema.domain

import ru.zhdanon.skillcinema.data.CinemaRepository
import ru.zhdanon.skillcinema.data.staffbyid.ResponseStaffById
import javax.inject.Inject

class GetStaffByIdUseCase @Inject constructor(private val repository: CinemaRepository) {

    suspend fun executeStaffById(staffId: Int): ResponseStaffById {
        return repository.getStaffById(staffId)
    }
}