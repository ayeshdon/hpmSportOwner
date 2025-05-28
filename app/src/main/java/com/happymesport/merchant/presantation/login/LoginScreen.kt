package com.happymesport.merchant.presantation.login

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.happymesport.merchant.R
import com.happymesport.merchant.common.extention.isValidSriLankaPhoneNumber
import com.happymesport.merchant.presantation.custom.LoadingDialog
import com.happymesport.merchant.presantation.custom.LoginRoundedButton
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.navigation.screens.SignInOtpScreen
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.lightText
import com.happymesport.merchant.presantation.theme.mainHeader

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun loginScreen(
    state: State<ViewState<String>>,
    onEvent: (AuthEvent) -> Unit,
    navController: NavController,
) {
    var spacing = LocalSpacing.current
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isFirstLoad by remember { mutableStateOf(true) }
    val invalidPhoneMessage = stringResource(id = R.string.validate_mobile_number)

    val activity = LocalContext.current as? Activity

    LaunchedEffect(state.value.data) {
        state.value.data?.let { verificationId ->
            navController.navigate(
                SignInOtpScreen(
                    mobileNumber = phoneNumber,
                    code = verificationId,
                ),
            )
            onEvent(AuthEvent.LoginWithMobileReset)
        }
    }

    LaunchedEffect(phoneNumber) {
        if (isFirstLoad) {
            isFirstLoad = false
            return@LaunchedEffect
        }
        if (!phoneNumber.isValidSriLankaPhoneNumber()) {
            errorMessage = invalidPhoneMessage
        } else {
            errorMessage = ""
        }
    }

    LoadingDialog(isLoading = state.value.isLoading)

    Scaffold(content = {
        Column(Modifier.padding(spacing.large)) {
            Box(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.what_is_your_mobile_number),
                color = MaterialTheme.colorScheme.onSurface,
                style = LocalTypography.current.mainHeader,
                modifier = Modifier.padding(top = 50.dp),
            )
            Box(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.please_enter_mobile_number_to_verify),
                color = MaterialTheme.colorScheme.onSurface,
                style = LocalTypography.current.bodyLarge,
                modifier = Modifier.padding(top = 10.dp),
            )

            Row(
                modifier =
                    Modifier
                        .padding(top = 40.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(spacing.cornerRadius))
                        .background(Color.White, RoundedCornerShape(spacing.cornerRadius)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "\uD83C\uDDF1\uD83C\uDDF0 +94",
                    style = LocalTypography.current.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp, start = spacing.default),
                )
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    placeholder = {
                        Text(
                            stringResource(id = R.string.enter_mobilea_number),
                            style = LocalTypography.current.bodyLarge,
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors =
                        TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            cursorColor = Color.Black,
                        ),
                )
            }
            Text(
                text = stringResource(id = R.string.eg_mobile_number),
                color = MaterialTheme.colorScheme.lightText,
                style = LocalTypography.current.bodyMedium,
                modifier = Modifier.padding(top = 0.dp, start = 16.dp),
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = LocalTypography.current.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

            Box(modifier = Modifier.height(20.dp))
            LoginRoundedButton(
                enable = errorMessage.isEmpty(),
                onClick = {
                    if (phoneNumber.isEmpty()) {
                        errorMessage = invalidPhoneMessage
                    } else {
                        activity?.let {
                            onEvent(AuthEvent.LoginWithMobile("+94$phoneNumber", it))
                        }
                    }
                },
                imageResource = null,
                buttonText =
                    stringResource(
                        id = R.string.login,
                    ),
            )
        }
    })
}
