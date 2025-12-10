package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.presantation.theme.AppThemeSecondary
import timber.log.Timber

@Composable
fun HourlyRateTextField(
    rate: String,
    label: String,
    onRateChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    borderColor: Color = AppThemeSecondary,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    var internalRate by remember { mutableStateOf(rate) }

    OutlinedTextField(
        prefix = { Text("LKR ") },
        value = internalRate,
        onValueChange = { input ->
            Timber.e("input : $input")
            Timber.e("formatted : $input")

            val parts = input.split('.')
            val formatted =
                when (parts.size) {
                    1 -> parts[0]
                    2 -> parts[0] + "." + parts[1].take(2)
                    else -> parts[0] + "." + parts[1].take(2)
                }

            internalRate = formatted
            onRateChange(formatted)
        },
        label = { Text(label) },
        placeholder = { Text("0.00") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        colors =
            OutlinedTextFieldDefaults.colors(
                errorBorderColor = errorBorderColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor.copy(alpha = 0.5f),
            ),
    )
}
