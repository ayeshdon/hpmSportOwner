package com.happymesport.merchant.presantation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.happymesport.merchant.R
import com.happymesport.merchant.presantation.custom.LoginRoundedButton
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.mainHeader
import timber.log.Timber

@Composable
fun signInOtpScreen(
    onEvent: (AuthEvent) -> Unit,
    mobileNumber: String,
    code: String,
    navController: NavController?,
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val enteredOtp by remember { derivedStateOf { otpValues.joinToString("") } }

    Timber.e("OTP PHONE : $mobileNumber")
    Timber.e("OTP CODE : $code")

    var spacing = LocalSpacing.current

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(spacing.default),
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(id = R.string.please_enter_mobile_number_to_verify),
            color = MaterialTheme.colorScheme.onSurface,
            style = LocalTypography.current.mainHeader,
            modifier = Modifier.padding(top = 20.dp),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(id = R.string.please_enter_mobile_number_to_verify) + " $mobileNumber",
            color = MaterialTheme.colorScheme.onSurface,
            style = LocalTypography.current.bodyLarge,
            modifier = Modifier.padding(top = 10.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                        Timber.e("LENGTH : ${enteredOtp.length}")
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

        LoginRoundedButton(
            enable = enteredOtp.length == 6,
            onClick = {
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

@Preview(showBackground = true)
@Composable
fun PreviewSignInOtpScreen() {
    MaterialTheme {
        signInOtpScreen(
            onEvent = {},
            mobileNumber = "9876543210",
            code = "123456",
            navController = null,
        )
    }
}
