package com.happymesport.merchant.presantation.facility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.happymesport.merchant.R
import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.presantation.custom.HPMAppBar
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.White
import com.happymesport.merchant.presantation.theme.textDark
import timber.log.Timber

@Composable
fun FacilityOpeningHourScreen(
    navController: NavHostController,
    name: String,
    description: String,
    selectedList: List<AvailableFacility>,
) {
    Timber.e("OPENING Name: $name, Description: $description")
    Timber.e("OPENING selectedList : ${selectedList.size}")
    Scaffold(
        topBar = {
            HPMAppBar(
                title = stringResource(id = R.string.add_new_facility),
                backNavigation = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = White,
                            contentDescription = "Localized description",
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(LocalSpacing.current.default),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.opening_hours),
                    color = textDark,
                    style = LocalTypography.current.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    )
}
