package com.happymesport.merchant.presantation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun dashboardScreen(
    navController: NavController?,
) {
    Box(modifier = Modifier.width(100.dp)) {
        Text(text = "Dashbaord")
    }
}
