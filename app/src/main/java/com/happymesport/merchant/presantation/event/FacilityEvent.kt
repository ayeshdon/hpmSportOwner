package com.happymesport.merchant.presantation.event

import com.happymesport.merchant.data.dto.AvailableFacility

sealed class FacilityEvent : BaseEvent {
    data object GetFacilityList : FacilityEvent()

    data class SaveFacilityGeneralData(
        val name: String,
        val description: String,
        val selectedList: List<AvailableFacility>,
    ) : FacilityEvent()
}
