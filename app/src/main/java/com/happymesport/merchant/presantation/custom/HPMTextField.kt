package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.R
import com.happymesport.merchant.presantation.theme.AppThemeSecondary

@Composable
fun HPMTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    cornerRadius: Dp = 8.dp,
    borderColor: Color = AppThemeSecondary,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    val actualTrailingIcon: @Composable (() -> Unit)? = { trailingIcon }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = actualTrailingIcon,
        shape = RoundedCornerShape(cornerRadius),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) errorBorderColor else focusedBorderColor,
                unfocusedBorderColor = if (isError) errorBorderColor else borderColor,
                errorBorderColor = errorBorderColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor.copy(alpha = 0.5f),
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomBorderedTextFields() {
    MaterialTheme {
        Column {
            var text1 by remember { mutableStateOf("") }
            HPMTextField(
                value = text1,
                onValueChange = { text1 = it },
                label = stringResource(id = R.string.facility_name),
                placeholder = stringResource(id = R.string.facility_name),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "User icon",
                    )
                },
                cornerRadius = 12.dp,
            )

            var text2 by remember { mutableStateOf("") }
            HPMTextField(
                value = text2,
                onValueChange = { text2 = it },
                label = "Password",
                keyboardType = KeyboardType.Password,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                isError = text2.length < 5 && text2.isNotEmpty(), // Example error state
                borderColor = Color.Gray, // Custom unfocused border color
                focusedBorderColor = Color.Blue, // Custom focused border color
                cornerRadius = 20.dp, // More rounded corners
                errorBorderColor = MaterialTheme.colorScheme.error, // Ensure error color is distinct
            )

            var text3 by remember { mutableStateOf("Disabled Text") }
            HPMTextField(
                value = text3,
                onValueChange = { text3 = it },
                label = "Disabled Field",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                enabled = false,
                containerColor = Color.LightGray.copy(alpha = 0.3f), // Custom disabled background
            )

            var text4 by remember { mutableStateOf("ReadOnly Text") }
            HPMTextField(
                value = text4,
                onValueChange = { text4 = it },
                label = "Read-Only Field",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                readOnly = true,
                containerColor = Color.LightGray.copy(alpha = 0.1f), // Custom read-only background
            )
        }
    }
}
