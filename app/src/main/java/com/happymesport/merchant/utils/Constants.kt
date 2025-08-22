package com.happymesport.merchant.utils

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object Constants {
    const val AUTH_SETTINGS = "Auth_pref"
    const val USER_SETTINGS = "User_pref"
    const val AUTH_TOKEN = "auth_token"
    const val USER_PROFILE_DATA = "user_profile_data"
    const val PROFILE_COMPLETE_FLAG = "profile_Complete_flag"
    const val REFRESH_TOKEN = "refresh_token"
}

val SRI_LANKA_BOUNDS =
    LatLngBounds(
        LatLng(5.92, 79.65), // Southwest corner (approx)
        LatLng(9.90, 81.90), // Northeast corner (approx)
    )
