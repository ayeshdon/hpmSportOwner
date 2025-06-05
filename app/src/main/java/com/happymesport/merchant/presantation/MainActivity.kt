package com.happymesport.merchant.presantation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.happymesport.merchant.presantation.navigation.authNavigation
import com.happymesport.merchant.presantation.theme.HappyMeSportMerchantTheme
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
