package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.presantation.theme.AppThemeSecondary
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.White

@Composable
fun CustomAppBar(
    title: String,
    onBackClick: (() -> Unit)?,
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val spacing = LocalSpacing.current
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(spacing.appBarHeight),
        shadowElevation = 4.dp,
        color = AppThemeSecondary,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(top = spacing.default)
                    .fillMaxSize(),
        ) {
            onBackClick?.let {
                IconButton(
                    onClick = onBackClick,
                    modifier =
                        Modifier
                            .align(Alignment.CenterStart),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White,
                    )
                }
            }

            Text(
                text = title,
                color = White,
                style = LocalTypography.current.titleLarge,
                modifier =
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = spacing.default),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomAppBarPreview() {
    CustomAppBar(
        title = "My Title",
        onBackClick = { /* no-op for preview */ },
    )
}
