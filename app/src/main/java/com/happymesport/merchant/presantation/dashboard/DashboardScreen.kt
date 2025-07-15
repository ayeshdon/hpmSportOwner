package com.happymesport.merchant.presantation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.happymesport.merchant.presantation.event.DashboardEvent
import com.happymesport.merchant.presantation.navigation.screens.UserProfileCreateScreen
import com.happymesport.merchant.presantation.state.ViewState
import timber.log.Timber

@Composable
fun dashboardScreen(
    navController: NavController?,
    profileState: State<ViewState<Boolean>>,
    onEvent: (DashboardEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(DashboardEvent.CheckProfileStatus)
    }
    LaunchedEffect(profileState.value) {
        Timber.e("DASHBOARD CHECK DATA : ${profileState.value.data}")
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
        DashboardView()
    }
}

@Composable
fun DashboardView() {
    val context = LocalContext.current

    Scaffold(
        topBar = { DashBoardAppBarView() },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column {
                    Text(text = "HOME PAGE")
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardViewPreview() {
    DashboardView()
}
