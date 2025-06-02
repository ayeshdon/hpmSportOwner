package com.happymesport.merchant.presantation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.happymesport.merchant.presantation.login.loginScreen
import com.happymesport.merchant.presantation.login.signInOtpScreen
import com.happymesport.merchant.presantation.navigation.screens.Onboarding
import com.happymesport.merchant.presantation.navigation.screens.SignInOtpScreen
import com.happymesport.merchant.presantation.navigation.screens.SignInScreen
import com.happymesport.merchant.presantation.onbaording.onBoardingScreen
import com.happymesport.merchant.presantation.vm.AuthViewModel

@Composable
fun authNavigation() {
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
    NavHost(navController = navController, startDestination = Onboarding) {
        composable<Onboarding> {
            val authViewModel: AuthViewModel = hiltViewModel()
            onBoardingScreen(
                {
                    navController.navigate(SignInScreen)
                },
                authViewModel::onEvent,
                authViewModel.stateAuthFlag.collectAsStateWithLifecycle(),
            )
        }
        composable<SignInScreen> {
            val authViewModel: AuthViewModel = hiltViewModel()
            loginScreen(
                onEvent = authViewModel::onEvent,
                state = authViewModel.stateLoginMobile.collectAsStateWithLifecycle(),
                navController = navController,
            )
        }

        composable<SignInOtpScreen> {
            val authViewModel: AuthViewModel = hiltViewModel()
            var arg = it.toRoute<SignInOtpScreen>()
            signInOtpScreen(
                state = authViewModel.stateLoginOtp.collectAsStateWithLifecycle(),
                onEvent = authViewModel::onEvent,
                mobileNumber = arg.mobileNumber,
                code = arg.code,
                navController = navController,
            )
        }
    }
}
