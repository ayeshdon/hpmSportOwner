package com.happymesport.merchant.presantation.custom

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.presantation.theme.AppBar
import com.happymesport.merchant.presantation.theme.AppThemePrimary
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HPMAppBar(
    elevation: Dp = 4.dp,
    title: String,
    backNavigation: @Composable (() -> Unit)? = null,
    actions: List<@Composable () -> Unit> = emptyList(),
) {
    CenterAlignedTopAppBar(
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = AppThemePrimary,
                titleContentColor = White,
            ),
        title = {
            Text(
                title,
                color = White,
                style = LocalTypography.current.AppBar,
            )
        },
        navigationIcon = { backNavigation?.invoke() },
        actions = {
            actions.forEach { action ->
                action()
            }
        },
        modifier = Modifier.shadow(elevation, shape = RectangleShape),
    )
}
