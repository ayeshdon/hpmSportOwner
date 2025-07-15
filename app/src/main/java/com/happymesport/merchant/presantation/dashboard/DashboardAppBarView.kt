package com.happymesport.merchant.presantation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.R
import com.happymesport.merchant.presantation.custom.ProfileAvatar
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.textDark
import com.happymesport.merchant.presantation.theme.textLight

@Composable
fun DashBoardAppBarView() {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val spacing = LocalSpacing.current
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(spacing.appBarHeight),
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = statusBarPadding, start = spacing.default, end = spacing.default)
                    .height(IntrinsicSize.Min),
        ) {
            ProfileAvatar(
                imageUrl = "",
                placeholderResId = R.drawable.vec_avater_place_holder,
            )
            Spacer(modifier = Modifier.weight(1f))
            nameView()
        }
    }
}

@Composable
fun nameView() {
    val spacing = LocalSpacing.current

    Column(modifier = Modifier.padding(start = spacing.small)) {
        Text(
            text = "Ayesh Nanayakkara",
            color = textDark,
            style = LocalTypography.current.titleMedium,
            modifier =
                Modifier
                    .align(Alignment.End),
        )
        Text(
            text = "Last Login 2025-06-04 05:45:34",
            color = textLight,
            style = LocalTypography.current.bodySmall,
            modifier =
                Modifier
                    .align(Alignment.End),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashBoardAppBarViewPreview() {
    DashBoardAppBarView()
}
