package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography

@Composable
fun AppErrorTextView(errorMessage: String) {
    Text(
        text = errorMessage,
        color = MaterialTheme.colorScheme.error,
        style = LocalTypography.current.bodyMedium,
        modifier =
            Modifier.padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.medium,
            ),
    )
}

@Preview
@Composable
fun AppErrorTextViewPreview() {
    AppErrorTextView(
        errorMessage = "This is an error message to be displayed in the AppErrorTextView.",
    )
}
