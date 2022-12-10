package com.example.feature_details.domain.repository_ipl

import com.example.feature_details.domain.DetailFilmsApi
import com.example.feature_details.domain.NetworkStaffDetailRepository

class NetworkStaffRepositoryImpl(
    private val staffApi: DetailFilmsApi
): NetworkStaffDetailRepository {

    override suspend fun getStaffByID(id: Int) = staffApi.getStaffByID(id)
}