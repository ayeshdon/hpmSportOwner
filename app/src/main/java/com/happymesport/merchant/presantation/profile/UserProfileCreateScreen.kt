package com.happymesport.merchant.presantation.profile

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.happymesport.merchant.R
import com.happymesport.merchant.common.extention.isValidEmail
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.presantation.custom.AppEditText
import com.happymesport.merchant.presantation.custom.CustomAppBar
import com.happymesport.merchant.presantation.custom.LoadingDialog
import com.happymesport.merchant.presantation.custom.LoginRoundedButton
import com.happymesport.merchant.presantation.event.UserProfileEvent
import com.happymesport.merchant.presantation.navigation.screens.DashboardScreen
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.theme.AppThemeSecondary
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.lightText
import com.happymesport.merchant.presantation.theme.white
import com.happymesport.merchant.utils.Utils
import timber.log.Timber
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileCreateScreen(
    navController: NavHostController,
    onEvent: (UserProfileEvent) -> Unit,
    state: State<ViewState<ProfileImageUploadIEffect>>,
    profileImgUploadState: State<ViewState<String>>,
    profileDetailsState: State<ViewState<UserModel?>>,
    profileUpdateState: State<ViewState<String>>,
) {
    val spacing = LocalSpacing.current

    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    val mContext = LocalContext.current

    val mDate = remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var profileUrl by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(profileDetailsState.value.data) {
        profileDetailsState.value.data?.let {
            it.imageUrl?.let {
                profileUrl = it
                Timber.e("IMAGE URL 2: $profileUrl")
            }
            name = it.name ?: ""
            email = it.email ?: ""
            mDate.value = it.dob ?: ""
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview(),
            onResult = { bitmap ->
                Timber.e("Camera image bitmap: $bitmap")
                if (bitmap != null) {
                    var uri =
                        Utils.saveBitmapToCacheAndGetUri(
                            mContext,
                            bitmap,
                            "${System.currentTimeMillis()}",
                        )
                    onEvent(UserProfileEvent.ImageSelected(uri!!))
                }
            },
        )

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                if (uri != null) {
                    onEvent(UserProfileEvent.ImageSelected(uri!!))
                }
            },
        )

    val mDatePickerDialog =
        DatePickerDialog(
            mContext,
            { _, year: Int, month: Int, day: Int ->
                mDate.value = "$day/${month + 1}/$year"
            },
            mYear,
            mMonth,
            mDay,
        )

    if (showDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                showDialog = false
            },
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.select_image),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = LocalTypography.current.titleLarge,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        showDialog = false
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.pick_from_Gallery),
                        color = MaterialTheme.colorScheme.white,
                        style = LocalTypography.current.titleMedium,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        showDialog = false
                        cameraLauncher.launch(null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.take_a_Photo),
                        color = MaterialTheme.colorScheme.white,
                        style = LocalTypography.current.titleMedium,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = {
                        showDialog = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = LocalTypography.current.titleMedium,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    LaunchedEffect(Unit) {
        onEvent(UserProfileEvent.GetProfileData)
    }

    LaunchedEffect(profileUpdateState.value.data) {
        profileUpdateState.value.data?.let {
            Toast
                .makeText(
                    mContext,
                    mContext.resources.getString(R.string.profile_update_success),
                    Toast.LENGTH_LONG,
                ).show()
            navController.popBackStack()
            navController.navigate(DashboardScreen) {
                popUpTo(DashboardScreen) {
                    inclusive = true
                }
            }
//            navController?.navigate(DashboardScreen) {
//                popUpTo(navController.graph.startDestinationId) {
//                    inclusive = true
//                }
//                launchSingleTop = true
//            }
        }
    }

    LaunchedEffect(profileImgUploadState.value.data) {
        profileImgUploadState.value.data?.let {
            profileUrl = it
            Timber.e("STATE PROFILE IMAGE $profileUrl")
            Toast
                .makeText(
                    mContext,
                    mContext.resources.getString(R.string.profile_image_upload_success),
                    Toast.LENGTH_LONG,
                ).show()
        }
    }
    LoadingDialog(isLoading = (profileImgUploadState.value.isLoading || profileUpdateState.value.isLoading))

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CustomAppBar(title = stringResource(id = R.string.profile), null)
        },
        content = { innerPadding ->

            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(spacing.default),
                shape = RoundedCornerShape(spacing.cornerRadius),
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.white,
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = spacing.cornerRadiusS),
            ) {
                Column(
                    modifier =
                        Modifier
                            .padding(spacing.default)
                            .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ProfileImageWithEditIcon(
                        onEditClick = {
                            showDialog = true
                        },
                        imageResource = R.drawable.vec_avater_place_holder,
                        profileImgUrl = "$profileUrl",
                    )

                    AppEditText(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.name),
                                color = MaterialTheme.colorScheme.lightText,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Name Icon",
                                tint = AppThemeSecondary,
                            )
                        },
                    )

                    AppEditText(
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.email),
                                color = MaterialTheme.colorScheme.lightText,
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = AppThemeSecondary,
                            )
                        },
                    )

                    AppEditText(
                        value = TextFieldValue(mDate.value).text,
                        onValueChange = { },
                        label = {
                            Text(
                                text = stringResource(id = R.string.dob),
                                color = MaterialTheme.colorScheme.lightText,
                            )
                        },
                        readOnly = true,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { mDatePickerDialog.show() },
                        leadingIcon = {
                            Icon(
                                Icons.Default.DateRange,
                                tint = AppThemeSecondary,
                                contentDescription = "Select Date",
                                modifier = Modifier.clickable { mDatePickerDialog.show() },
                            )
                        },
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = "* $errorMessage",
                            color = MaterialTheme.colorScheme.error,
                            style = LocalTypography.current.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }

                    LoginRoundedButton(
                        enable = true,
                        onClick = {
                            if (name.isEmpty()) {
                                errorMessage = "Please enter name"
                            } else if (!email.isValidEmail()) {
                                errorMessage = "Please enter valid email address"
                            } else if (mDate.value.isEmpty()) {
                                errorMessage = "Please enter Date of Birth"
                            } else {
                                errorMessage = ""
                                onEvent(
                                    UserProfileEvent.UpdateProfile(
                                        name = name,
                                        email = email,
                                        dob = mDate.value,
                                    ),
                                )
                            }
                        },
                        imageResource = null,
                        buttonText =
                            stringResource(
                                id = R.string.save,
                            ),
                    )
                }
            }
        },
    )
}

@Composable
fun ProfileImageWithEditIcon(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    imageResource: Int,
    profileImgUrl: String,
) {
    Timber.e("SET PROFILE IMAGE : $profileImgUrl")
    Box(
        modifier = modifier.size(100.dp),
    ) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(profileImgUrl)
                    .crossfade(true)
                    .build(),
            contentDescription = "Image loaded from URL",
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        CircleShape,
                    ),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(imageResource),
            error = painterResource(imageResource),
        )

        Surface(
            modifier =
                Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .clickable { onEditClick() }
                    .padding(10.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.white,
            shadowElevation = 8.dp,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile Picture",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clickable { onEditClick() }
                        .padding(3.dp),
            )
        }
    }
}
