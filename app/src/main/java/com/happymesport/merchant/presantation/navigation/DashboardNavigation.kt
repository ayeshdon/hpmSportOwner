package com.happymesport.merchant.presantation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.happymesport.merchant.presantation.dashboard.dashboardScreen
import com.happymesport.merchant.presantation.navigation.screens.DashboardScreen

@Composable
fun dashboardNavigation() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true
    val backgroundColor = Color.White // or MaterialTheme.colorScheme.background

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = useDarkIcons,
        )
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DashboardScreen) {
        composable<DashboardScreen> {
            // val authViewModel: AuthViewModel = hiltViewModel()
            dashboardScreen(
                navController = navController,
            )
        }
    }
}
