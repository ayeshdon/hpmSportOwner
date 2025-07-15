package com.happymesport.merchant.presantation.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.happymesport.merchant.presantation.theme.AppThemeLight
import com.happymesport.merchant.presantation.theme.LocalSpacing

@Composable
fun ProfileAvatar(
    imageUrl: String?,
    @DrawableRes placeholderResId: Int,
    size: Dp = 40.dp,
    contentDescription: String = "Profile image",
) {
    val imageModel =
        remember(imageUrl) {
            if (!imageUrl.isNullOrBlank()) imageUrl else placeholderResId
        }

    AsyncImage(
        model = imageModel,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(LocalSpacing.current.imageBorder, AppThemeLight, CircleShape)
    )
}
