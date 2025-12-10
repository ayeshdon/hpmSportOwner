package com.happymesport.merchant.domain.repository

import com.happymesport.merchant.data.dto.FacilityDto

interface FacilityRepository {
    suspend fun getFacilityByUserId(uid: String): FacilityDto?
    suspend fun saveFacilityDetails(facilityDto: FacilityDto) : String
}
