package com.happymesport.merchant.presantation.login

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.happymesport.merchant.R
import com.happymesport.merchant.presantation.DashboardActivity
import com.happymesport.merchant.presantation.custom.LoginRoundedButton
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.mainHeader
import timber.log.Timber

@Composable
fun signInOtpScreen(
    state: State<ViewState<String>>,
    onEvent: (AuthEvent) -> Unit,
    mobileNumber: String,
    code: String,
    navController: NavController?,
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val enteredOtp by remember { derivedStateOf { otpValues.joinToString("") } }
    var errorMessage by remember { mutableStateOf("") }

    var spacing = LocalSpacing.current
    var context = LocalContext.current

    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            errorMessage = it
        }
    }
    LaunchedEffect(state.value.data) {
        state.value.data?.let {
            var intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(spacing.default),
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(id = R.string.please_enter_mobile_number_to_verify),
            color = MaterialTheme.colorScheme.onSurface,
            style = LocalTypography.current.mainHeader,
            modifier = Modifier.padding(top = 20.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.please_enter_mobile_number_to_verify) + " $mobileNumber",
            color = MaterialTheme.colorScheme.onSurface,
            style = LocalTypography.current.bodyLarge,
            modifier = Modifier.padding(top = 10.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            otpValues[index] = newValue
                            if (newValue.isNotEmpty() && index < 5) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    modifier =
                        Modifier
                            .width(48.dp)
                            .height(56.dp)
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent {
                                if (it.key == Key.Backspace && otpValues[index].isEmpty()) {
                                    if (index > 0) {
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                }
                                false
                            },
                    singleLine = true,
                    textStyle =
                        LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                        ),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (index == 5) ImeAction.Done else ImeAction.Next,
                        ),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = LocalTypography.current.bodyMedium,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        LoginRoundedButton(
            enable = enteredOtp.length == 6,
            onClick = {
                onEvent(
                    AuthEvent.LoginWithMobileOtpVerify(
                        verificationId = code,
                        otp = enteredOtp,
                        phone = mobileNumber,
                    ),
                )
            },
            imageResource = null,
            buttonText =
                stringResource(
                    id = R.string.verify_otp,
                ),
        )
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}
