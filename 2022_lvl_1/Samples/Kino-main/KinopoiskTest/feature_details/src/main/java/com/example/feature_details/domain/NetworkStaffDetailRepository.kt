package com.example.feature_details.domain

import com.example.feature_details.data.detailsFilm_page.dto.StaffDetailsDTO

interface NetworkStaffDetailRepository {

  suspend fun  getStaffByID(id:Int): StaffDetailsDTO
}