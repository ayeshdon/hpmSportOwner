package com.happymesport.merchant.presantation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.happymesport.merchant.presantation.navigation.dashboardNavigation
import com.happymesport.merchant.presantation.theme.HappyMeSportMerchantTheme

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HappyMeSportMerchantTheme {
                dashboardNavigation()
            }
        }
    }
}
