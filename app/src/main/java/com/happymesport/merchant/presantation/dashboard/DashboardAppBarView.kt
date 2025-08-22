package com.happymesport.merchant.presantation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.R
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.presantation.custom.ProfileAvatar
import com.happymesport.merchant.presantation.theme.AppThemePrimary
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.White
import timber.log.Timber

@Composable
fun DashBoardAppBarView(userModel: UserModel?) {

    val spacing = LocalSpacing.current
    Surface(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .background(AppThemePrimary)
                    .fillMaxWidth()
                    .padding(spacing.default),
        ) {
            ProfileAvatar(
                size = 80.dp,
                imageUrl = userModel?.imageUrl,
                placeholderResId = R.drawable.vec_avater_place_holder,
            )
            Spacer(modifier = Modifier.width(20.dp))
            nameView(userModel?.name ?: "", userModel?.email ?: "")
        }
    }
}

@Composable
fun nameView(
    name: String,
    email: String,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.padding(start = spacing.small, top = spacing.default),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = name,
            color = White,
            style = LocalTypography.current.titleLarge,
            modifier =
                Modifier
                    .align(Alignment.Start),
        )
        Text(
            text = email,
            color = White,
            style = LocalTypography.current.titleMedium,
            modifier =
                Modifier
                    .align(Alignment.Start),
        )
    }
}
