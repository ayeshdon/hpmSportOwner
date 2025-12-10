package com.happymesport.merchant.presantation.event

import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.data.dto.FacilityDto

sealed class FacilityEvent : BaseEvent {
    data object GetFacilityList : FacilityEvent()

    data class AddFacilityList(
        val facilityDto: FacilityDto,
    ) : FacilityEvent()
}
