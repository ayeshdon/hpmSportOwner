package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.presantation.theme.AppThemeSecondary
import com.happymesport.merchant.presantation.theme.LocalTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourDropdown(
    label: String,
    selectedHour: Int,
    onHourSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    borderColor: Color = AppThemeSecondary,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    var expanded by remember { mutableStateOf(false) }
    val hours = (1..23).toList()
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = "$selectedHour:00",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, style = LocalTypography.current.labelMedium) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            shape = RoundedCornerShape(cornerRadius),
            colors =
                OutlinedTextFieldDefaults.colors(
                    errorBorderColor = errorBorderColor,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor.copy(alpha = 0.5f),
                ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            hours.forEach { hour ->
                DropdownMenuItem(
                    text = { Text("$hour:00", style = LocalTypography.current.labelMedium) },
                    onClick = {
                        onHourSelected(hour)
                        expanded = false
                    },
                )
            }
        }
    }
}
