package com.happymesport.merchant.domain.usecase.facility

import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.data.dto.FacilityDto
import com.happymesport.merchant.domain.repository.FacilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetFacilityByUserUseCase
    @Inject
    constructor(
        private val repository: FacilityRepository,
    ) {
        suspend operator fun invoke(uid: String): Flow<Resources<FacilityDto>> =
            flow {
                try {
                    Timber.e("VM_CHECK UC 01")
                    emit(Resources.Loading())
                    var result = repository.getFacilityByUserId(uid)
                    emit(Resources.Success(result))
                } catch (e: Exception) {
                    Timber.e("VM_CHECK EXCEPTION 01 :${e.message}")
                    e.printStackTrace()
                    emit(Resources.Error(message = "Server error: ${e.message} ", exception = e))
                }
            }
    }
