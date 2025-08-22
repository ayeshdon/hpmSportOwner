package com.happymesport.merchant.presantation.facility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.happymesport.merchant.R
import com.happymesport.merchant.presantation.theme.AppThemePrimary
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.utils.SRI_LANKA_BOUNDS
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.Locale

@Composable
fun FacilityLocationPickMapView(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationSelected: (LatLng) -> Unit,
) {
    val context = LocalContext.current
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }

    val locationPermissionDeniceMSg = stringResource(R.string.location_permission_denied)
    val outSideBoundMsg = stringResource(R.string.outSide_sri_lanka)
    val currentLocationSwitchMsg = stringResource(R.string.current_location_switching)

    val cameraPositionState =
        rememberCameraPositionState {
            position =
                CameraPosition.fromLatLngZoom(LatLng(7.8731, 80.7718), 18f) // Center of Sri Lanka
        }

    val coroutineScope = rememberCoroutineScope()
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            hasLocationPermission = isGranted
            if (!isGranted) {
                Toast.makeText(context, locationPermissionDeniceMSg, Toast.LENGTH_SHORT).show()
            }
        }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    if (SRI_LANKA_BOUNDS.contains(currentLatLng)) {
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng,
                                    15f,
                                ),
                            )
                        }
                    } else {
                        Toast.makeText(context, outSideBoundMsg, Toast.LENGTH_LONG).show()
                    }
                } ?: run {
                    Toast.makeText(context, currentLocationSwitchMsg, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(LocalSpacing.current.default)
                .clip(RoundedCornerShape(LocalSpacing.current.cornerRadiusS)) // Apply round corners
                .border(1.dp, AppThemePrimary, RoundedCornerShape(LocalSpacing.current.cornerRadiusS)),
        contentAlignment = Alignment.Center,
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties =
                MapProperties(
                    isMyLocationEnabled = hasLocationPermission,
                    mapType = MapType.TERRAIN,
                ),
            uiSettings =
                MapUiSettings(
                    zoomControlsEnabled = true,
                    compassEnabled = true,
                    myLocationButtonEnabled = true,
                    rotationGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    tiltGesturesEnabled = true,
                    zoomGesturesEnabled = true,
                ),
            onMapLoaded = {
                // Optional: You can do something after the map loads
            },
            onMapClick = { latLng ->
                // This won't be used for 'ping' selection as we're tracking camera center
            },
            onPOIClick = { poi ->
                // Handle POI clicks if needed
            },
        ) {
            // Marker at the selected location (center of the map)
            selectedLocation?.let {
                Marker(
                    state = rememberMarkerState(position = it),
                    title = "Selected Location",
                    snippet = "Latitude: ${it.latitude}, Longitude: ${it.longitude}",
                )
            }
        }

        // A "ping" icon/marker in the center of the map
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Map Center Pin",
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
            tint = MaterialTheme.colorScheme.primary,
        )

        // Button to confirm selection
        Button(
            onClick = {
                // The "selected location" is the current center of the map
                val centerLatLng = cameraPositionState.position.target

                if (SRI_LANKA_BOUNDS.contains(centerLatLng)) {
                    selectedLocation = centerLatLng
                    onLocationSelected(centerLatLng)
                } else {
                    Toast
                        .makeText(
                            context,
                            "Please select a location within Sri Lanka.",
                            Toast.LENGTH_LONG,
                        ).show()
                }
            },
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
        ) {
            Text("Confirm Location")
        }

        // Listen for camera position changes to update the "ping" location
        LaunchedEffect(cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                val currentCenter = cameraPositionState.position.target
                // You could optionally reverse geocode here to show address
                var address = getAddressFromLatLng(context, currentCenter)
                Timber.e("Address: $address")
            }
        }
    }
//    }
}

// Optional: Function to get address from LatLng using Geocoder
fun getAddressFromLatLng(
    context: Context,
    latLng: LatLng,
): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            // You can format the address as needed
            return address.getAddressLine(0) ?: "Unknown Address"
        }
    } catch (e: IOException) {
        Log.e("MapSelection", "Geocoder failed: ${e.message}")
    }
    return "No address found"
}
