package com.happymesport.merchant.presantation.event

sealed class DashboardEvent : BaseEvent {
    data object CheckProfileStatus : DashboardEvent()
}
