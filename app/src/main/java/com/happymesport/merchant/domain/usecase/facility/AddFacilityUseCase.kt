package com.happymesport.merchant.domain.usecase.facility

import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.data.dto.FacilityDto
import com.happymesport.merchant.domain.repository.FacilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFacilityUseCase
    @Inject
    constructor(
        private val repository: FacilityRepository,
    ) {
        suspend operator fun invoke(facilityDto: FacilityDto): Flow<Resources<String>> =
            flow {
                try {
                    emit(Resources.Loading())
                    var result = repository.saveFacilityDetails(facilityDto)
                    emit(Resources.Success(result))
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resources.Error(message = "Server error: ${e.message} ", exception = e))
                }
            }
    }
