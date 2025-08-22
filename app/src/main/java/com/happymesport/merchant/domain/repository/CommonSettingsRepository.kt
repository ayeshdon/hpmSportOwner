package com.happymesport.merchant.domain.repository

import com.happymesport.merchant.data.dto.AvailableFacility
import kotlinx.coroutines.flow.Flow

interface CommonSettingsRepository {
    suspend fun getAvailableFacilities(): List<AvailableFacility>
}
