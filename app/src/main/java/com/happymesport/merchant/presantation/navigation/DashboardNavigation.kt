package com.happymesport.merchant.presantation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.happymesport.merchant.presantation.dashboard.DashboardViewModel
import com.happymesport.merchant.presantation.dashboard.dashboardScreen
import com.happymesport.merchant.presantation.navigation.screens.DashboardScreen
import com.happymesport.merchant.presantation.navigation.screens.UserProfileCreateScreen
import com.happymesport.merchant.presantation.profile.UserProfileCreateScreen
import com.happymesport.merchant.presantation.profile.UserProfileViewModel

@Composable
fun dashboardNavigation() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true
    val backgroundColor = Color.White // or MaterialTheme.colorScheme.background

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = false,
        )
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DashboardScreen) {
        composable<DashboardScreen> {
            val viewModel: DashboardViewModel = hiltViewModel()
            dashboardScreen(
                navController = navController,
                onEvent = viewModel::onEvent,
                profileState = viewModel.profileCompleteState.collectAsState(),
            )
        }
        composable<UserProfileCreateScreen> {
            val viewModel: UserProfileViewModel = hiltViewModel()
            UserProfileCreateScreen(
                navController = navController,
                onEvent = viewModel::onEvent,
                state = viewModel.profileImgPickerState.collectAsState(),
                profileImgUploadState = viewModel.profileImgUploadState.collectAsState(),
                profileDetailsState = viewModel.profileDetailsState.collectAsStateWithLifecycle(),
                profileUpdateState = viewModel.profileUpdateState.collectAsStateWithLifecycle(),
            )
        }
    }
}
