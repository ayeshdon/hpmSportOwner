package com.happymesport.merchant.presantation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.happymesport.merchant.presantation.login.loginScreen
import com.happymesport.merchant.presantation.navigation.authNavigation
import com.happymesport.merchant.presantation.navigation.screens.Onboarding
import com.happymesport.merchant.presantation.navigation.screens.SignInScreen
import com.happymesport.merchant.presantation.onbaording.onBoardingScreen
import com.happymesport.merchant.presantation.theme.HappyMeSportMerchantTheme
import com.happymesport.merchant.presantation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var showSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(1000)
            showSplashScreen = false
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                showSplashScreen
            }
        }
        enableEdgeToEdge()



        setContent {
            HappyMeSportMerchantTheme {
               authNavigation()
            }
        }
    }
}
