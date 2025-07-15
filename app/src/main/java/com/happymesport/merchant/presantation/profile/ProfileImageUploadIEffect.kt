package com.happymesport.merchant.presantation.profile

sealed class ProfileImageUploadIEffect {
    data object ShowAlertImagePicker : ProfileImageUploadIEffect()
}
