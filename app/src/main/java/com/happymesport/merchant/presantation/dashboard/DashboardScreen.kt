package com.happymesport.merchant.presantation.dashboard

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.happymesport.merchant.data.dto.FacilityDto
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.presantation.DashboardActivity
import com.happymesport.merchant.presantation.event.DashboardEvent
import com.happymesport.merchant.presantation.navigation.screens.FacilityAddScreen
import com.happymesport.merchant.presantation.navigation.screens.UserProfileCreateScreen
import com.happymesport.merchant.presantation.facility.FacilityCreateView
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.theme.AppThemePrimary
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun dashboardScreen(
    navController: NavController?,
    profileState: State<ViewState<Boolean>>,
    onEvent: (DashboardEvent) -> Unit,
    profileDetailsState: State<ViewState<UserModel>>,
    facilityDataState: State<ViewState<FacilityDto>>,
    logoutEvent: SharedFlow<Unit>,
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = AppThemePrimary,
            darkIcons = false,
        )
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        logoutEvent.collectLatest {
            var intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
    LaunchedEffect(Unit) {
        onEvent(DashboardEvent.CheckProfileStatus)
        onEvent(DashboardEvent.GetProfileData)
        onEvent(DashboardEvent.GetFacilityData)
    }
    LaunchedEffect(profileState.value) {
        if (profileState.value.data == false) {
            navController?.navigate(UserProfileCreateScreen) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        DashboardView(navController, profileDetailsState, facilityDataState)
    }
}

@Composable
fun DashboardView(
    navController: NavController?,
    profileDetailsState: State<ViewState<UserModel>>,
    facilityDataState: State<ViewState<FacilityDto>>,
) {
    val context = LocalContext.current

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Timber.e("FACILITY DATA IN HOME PAGE : ${facilityDataState.value}")
                Column {
                    DashBoardAppBarView(profileDetailsState.value.data)
                    if (facilityDataState.value.data == null) {
                        FacilityCreateView(onAddFacilityClick = {
                            navController?.navigate(FacilityAddScreen)
                        })
                    } else {
                    }
                }
            }
        },
    )
}
