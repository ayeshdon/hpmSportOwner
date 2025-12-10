package com.happymesport.merchant.presantation.facility

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.happymesport.merchant.R
import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.presantation.custom.HPMAppBar
import com.happymesport.merchant.presantation.custom.HourDropdown
import com.happymesport.merchant.presantation.custom.HourlyRateTextField
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
    uid: String,
    sportName: String,
    sportId: String,
    sportUrl: String,
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
                Box(modifier = Modifier.height(LocalSpacing.current.default))
                WeekdaysView(
                    stringResource(id = R.string.weekdays),
                    onEndHourSelected = { Timber.e("End Hour: $it") },
                    onStartHourSelected = { Timber.e("Start Hour: $it") },
                    onRateSelected = { Timber.e("Hourly Rate: $it") },
                )
                Box(modifier = Modifier.height(LocalSpacing.current.large))

                WeekdaysView(
                    stringResource(id = R.string.weekends),
                    onEndHourSelected = { Timber.e("End Hour: $it") },
                    onStartHourSelected = { Timber.e("Start Hour: $it") },
                    onRateSelected = { Timber.e("Hourly Rate: $it") },
                )

                Button(
                    onClick = {
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                ) {
                    Text(
                        stringResource(id = R.string.add_opening_hours),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        },
    )
}

@Composable
fun WeekdaysView(
    label: String,
    onStartHourSelected: (Int) -> Unit,
    onEndHourSelected: (Int) -> Unit,
    onRateSelected: (Int) -> Unit,
) {
    var startHour by remember { mutableIntStateOf(1) }
    var endHour by remember { mutableIntStateOf(23) }
    var hourlyRate by remember { mutableStateOf("") }

    Column {
        Text(
            text = label,
            color = textDark,
            style = LocalTypography.current.titleMedium,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier =
                Modifier.padding(
                    top = LocalSpacing.current.medium,
                    bottom = LocalSpacing.current.medium,
                ),
        ) {
            HourDropdown(
                stringResource(id = R.string.start_hours),
                startHour,
                {
                    startHour = it
                    onStartHourSelected(it)
                },
                Modifier.weight(1f),
                containerColor = White,
            )
            HourDropdown(
                stringResource(id = R.string.end_hours),
                endHour,
                {
                    endHour = it
                    onEndHourSelected(it)
                },
                Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                containerColor = White,
            )
        }
        HourlyRateTextField(
            rate = hourlyRate,
            label = stringResource(id = R.string.hourly_rate),
            onRateChange = {
                hourlyRate = it
                onRateSelected(it.toIntOrNull() ?: 0)
            },
            modifier =
                Modifier.padding(
                    top = LocalSpacing.current.medium,
                    bottom = LocalSpacing.current.medium,
                ),
            containerColor = White,
        )
    }
}
