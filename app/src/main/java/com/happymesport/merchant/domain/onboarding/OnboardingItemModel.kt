package com.happymesport.merchant.domain.onboarding

import androidx.annotation.DrawableRes
import com.happymesport.merchant.R
import com.happymesport.merchant.common.UiText

sealed class OnboardingItemModel(
    @DrawableRes val icon: Int,
    val title: UiText,
    val description: UiText,
) {
    data object FirstPage : OnboardingItemModel(
        icon = R.drawable.ic_launcher_background,
        title = UiText.StringResources(R.string.onboard_title_01),
        description = UiText.StringResources(R.string.onboard_des_01),
    )

    data object SecondPage : OnboardingItemModel(
        icon = R.drawable.ic_launcher_foreground,
        title = UiText.StringResources(R.string.onboard_title_02),
        description = UiText.StringResources(R.string.onboard_des_02),
    )

    data object ThirdPages : OnboardingItemModel(
        icon = R.drawable.ic_launcher_background,
        title = UiText.StringResources(R.string.onboard_title_03),
        description = UiText.StringResources(R.string.onboard_des_03),
    )
}
