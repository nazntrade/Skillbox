package com.becker.beckerSkillCinema.presentation.filmDetail.staff.staffDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.becker.beckerSkillCinema.data.staffById.ResponseStaffById
import com.becker.beckerSkillCinema.domain.GetStaffByIdUseCase
import com.becker.beckerSkillCinema.presentation.StateLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffDetailViewModel @Inject constructor(
    private val getStaffByIdUseCase: GetStaffByIdUseCase
) : ViewModel() {

    private val _currentStaff = MutableStateFlow<ResponseStaffById?>(null)
    val currentStaff = _currentStaff.asStateFlow()

    private val _loadCurrentStaff = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadCurrentStaff = _loadCurrentStaff.asStateFlow()

    fun getStaffDetail(staffId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadCurrentStaff.value = StateLoading.Loading
                val staff = getStaffByIdUseCase.executeStaffById(staffId)
                _currentStaff.emit(staff)
                _loadCurrentStaff.value = StateLoading.Success
            } catch (e: Exception) {
                _loadCurrentStaff.value = StateLoading.Error(e.message.toString())
            }
        }
    }
}