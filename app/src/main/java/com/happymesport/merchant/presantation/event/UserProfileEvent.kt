package com.happymesport.merchant.presantation.event

import android.net.Uri

sealed class UserProfileEvent : BaseEvent {
    data object GetProfileData : UserProfileEvent()

    data class ImageSelected(
        val uri: Uri,
    ) : UserProfileEvent()

    data object PickImage : UserProfileEvent()

    data class UpdateProfile(
        val name: String,
        val email: String,
        val dob: String,
    ) : UserProfileEvent()
}
