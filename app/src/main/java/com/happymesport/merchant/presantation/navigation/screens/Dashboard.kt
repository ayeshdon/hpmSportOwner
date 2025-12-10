package com.happymesport.merchant.presantation.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
object DashboardScreen

@Serializable
object UserProfileCreateScreen

@Serializable
object FacilityAddScreen

@Serializable
class  FacilityAddOpeningHoursScreen(
    val uid: String,
    val sportName: String,
    val sportId: String,
    val sportUrl: String,
)
