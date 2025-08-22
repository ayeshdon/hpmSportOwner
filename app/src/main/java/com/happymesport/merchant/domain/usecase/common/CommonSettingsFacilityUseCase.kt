package com.happymesport.merchant.domain.usecase.common

import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.domain.repository.CommonSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommonSettingsFacilityUseCase
    @Inject
    constructor(
        private val repository: CommonSettingsRepository,
    ) {
        suspend operator fun invoke(): Flow<Resources<List<AvailableFacility>>> =
            flow {
                try {
                    emit(Resources.Loading())
                    var result = repository.getAvailableFacilities()
                    emit(Resources.Success(result))
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resources.Error(message = "Server error: ${e.message} ", exception = e))
                }
            }
    }
