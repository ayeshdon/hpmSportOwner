package com.happymesport.merchant.presantation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.happymesport.merchant.presantation.dashboard.DashboardViewModel
import com.happymesport.merchant.presantation.dashboard.dashboardScreen
import com.happymesport.merchant.presantation.facility.FacilityAddMainView
import com.happymesport.merchant.presantation.facility.FacilityCreateViewModel
import com.happymesport.merchant.presantation.facility.FacilityOpeningHourScreen
import com.happymesport.merchant.presantation.navigation.screens.DashboardScreen
import com.happymesport.merchant.presantation.navigation.screens.FacilityAddOpeningHoursScreen
import com.happymesport.merchant.presantation.navigation.screens.FacilityAddScreen
import com.happymesport.merchant.presantation.navigation.screens.UserProfileCreateScreen
import com.happymesport.merchant.presantation.profile.UserProfileCreateScreen
import com.happymesport.merchant.presantation.profile.UserProfileViewModel
import com.happymesport.merchant.presantation.theme.AppThemePrimary

@Composable
fun dashboardNavigation() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val backgroundColor = Color.White

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = AppThemePrimary,
            darkIcons = useDarkIcons,
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
                profileDetailsState = viewModel.profileDataState.collectAsStateWithLifecycle(),
                facilityDataState = viewModel.facilityDataState.collectAsStateWithLifecycle(),
                logoutEvent = viewModel.logoutEvent,
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
        composable<FacilityAddScreen> { backStackEntry ->
            val viewModel: FacilityCreateViewModel = hiltViewModel(backStackEntry)
//            val viewModel = hiltViewModel<FacilityCreateViewModel>()
            FacilityAddMainView(
                navController = navController,
                facilityCommonList = viewModel.facilityCommonDataState.collectAsStateWithLifecycle(),
                onEvent = viewModel::onEvent,
            )
        }

        composable<FacilityAddOpeningHoursScreen> {
            val viewModel: FacilityCreateViewModel =
                if (navController.previousBackStackEntry != null) {
                    hiltViewModel(
                        navController.previousBackStackEntry!!,
                    )
                } else {
                    hiltViewModel()
                }
            //    val viewModel = hiltViewModel<FacilityCreateViewModel>()
            FacilityOpeningHourScreen(
                navController = navController,
                name = viewModel.name.collectAsState().value,
                description = viewModel.description.collectAsState().value,
                selectedList = viewModel.selectedList.collectAsState().value,
            )
        }
    }
}
