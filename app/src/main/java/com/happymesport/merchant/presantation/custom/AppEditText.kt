package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F0F0), // A light grey for the background
            unfocusedContainerColor = Color(0xFFF0F0F0), // Same color for unfocused state
            focusedIndicatorColor = Color.Transparent, // Hides the focused underline/border
            unfocusedIndicatorColor = Color.Transparent, // Hides the unfocused underline/border
            disabledIndicatorColor = Color.Transparent, // Hides the disabled underline/border
            errorIndicatorColor = Color.Transparent, // Hides the error underline/border
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedLeadingIconColor = Color.Gray,
            unfocusedLeadingIconColor = Color.Gray,
        ),
        shape = RoundedCornerShape(10.dp),
    )
}