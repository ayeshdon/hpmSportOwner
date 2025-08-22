package com.happymesport.merchant.presantation.facility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.happymesport.merchant.presantation.custom.AppErrorTextView
import com.happymesport.merchant.presantation.custom.HPMAppBar
import com.happymesport.merchant.presantation.custom.HPMTextField
import com.happymesport.merchant.presantation.custom.LoadingDialog
import com.happymesport.merchant.presantation.event.FacilityEvent
import com.happymesport.merchant.presantation.navigation.screens.FacilityAddOpeningHoursScreen
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.White
import timber.log.Timber

@Composable
fun FacilityAddMainView(
    navController: NavHostController,
    facilityCommonList: State<ViewState<List<AvailableFacility>>>,
    onEvent: (FacilityEvent) -> Unit,
) {
    LoadingDialog(isLoading = (facilityCommonList.value.isLoading))
    LaunchedEffect(Unit) {
        onEvent(FacilityEvent.GetFacilityList)
    }

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
                    "Facility add screen",
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                FacilityAddForm(facilityCommonList) { name, description, selectedList ->
                    Timber.e("Facility Name: $name, Description: $description")
                    Timber.e("Selected Sports: ${selectedList.size}")
                    onEvent(
                        FacilityEvent.SaveFacilityGeneralData(
                            name = name,
                            description = description,
                            selectedList = selectedList,
                        ),
                    )
                    navController.navigate(
                        FacilityAddOpeningHoursScreen(),
                    )
                }
            }
        },
    )
}

@Composable
fun FacilityAddForm(
    facilityCommonList: State<ViewState<List<AvailableFacility>>>,
    onNextFacilityClick: (name: String, description: String, selectedSportList: List<AvailableFacility>) -> Unit,
) {
    var facilityName by remember { mutableStateOf("") }
    var facilityDescription by remember { mutableStateOf("") }
    val selectedList = remember { mutableStateListOf<AvailableFacility>() }
    var errorText by remember { mutableStateOf<String?>(null) }

    val errorFacilityName = stringResource(id = R.string.enter_facility_name)
    val errorFacilityDirection = stringResource(id = R.string.enter_facility_description)
    val errorFacilitySelect = stringResource(id = R.string.select_facility)

//    var fusedLocationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(
//            LocalContext.current,
//        )
    Column {
        Text(
            text = stringResource(id = R.string.enter_details),
            modifier = Modifier.fillMaxWidth(),
            style = LocalTypography.current.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        HPMTextField(
            value = facilityName,
            onValueChange = { facilityName = it },
            label = stringResource(id = R.string.facility_name),
            placeholder = stringResource(id = R.string.facility_name),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(LocalSpacing.current.medium),
            cornerRadius = LocalSpacing.current.cornerRadius,
            containerColor = White,
        )

        HPMTextField(
            value = facilityDescription,
            onValueChange = { facilityDescription = it },
            label = stringResource(id = R.string.description),
            placeholder = stringResource(id = R.string.description),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(LocalSpacing.current.medium)
                    .height(100.dp),
            cornerRadius = LocalSpacing.current.cornerRadius,
            containerColor = White,
        )

        facilityCommonList.value.data?.let {
            Text(
                text = stringResource(id = R.string.select_available_sport),
                modifier = Modifier.fillMaxWidth(),
                style = LocalTypography.current.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            FacilityPickView(it, selectedList) { selectedFacility ->
                Timber.e("Selected Facility: ${selectedFacility.name}")
                if (selectedList.contains(selectedFacility)) {
                    selectedList.remove(selectedFacility)
                } else {
                    selectedList.add(selectedFacility)
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(
                    40.dp,
                ),
        )
        errorText?.let {
            AppErrorTextView(errorMessage = it)
        }
        Button(
            onClick = {
                if (facilityName.isEmpty()) {
                    errorText = errorFacilityName
                } else if (facilityDescription.isEmpty()) {
                    errorText = errorFacilityDirection
                } else if (selectedList.isEmpty()) {
                    errorText = errorFacilitySelect
                } else {
                    errorText = null
                    onNextFacilityClick(
                        facilityName,
                        facilityDescription,
                        facilityCommonList.value.data ?: emptyList(),
                    )
                }
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

//        }

//        FacilityLocationPickMapView(fusedLocationClient = fusedLocationClient) { latLng ->
//            Timber.e("Selected Location: ${latLng.latitude}, ${latLng.longitude}")
//        }
    }
}

@Composable
fun FacilityPickView(
    facilityList: List<AvailableFacility>,
    selectedList: List<AvailableFacility>,
    onSelected: (AvailableFacility) -> Unit,
) {
    LazyRow(
        modifier =
            Modifier
                .padding(horizontal = 16.dp),
    ) {
        items(facilityList) { item ->
            FacilityItem(
                facility = item,
                isSelected = selectedList.contains(item),
                onClick = onSelected,
            )
        }
    }
}
