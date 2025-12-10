package com.happymesport.merchant.ui

import androidx.compose.runtime.Composable
import com.happymesport.common.ui.AlertFactory
import com.rmd.app.core.ui.dialogs.AlertType

@Composable
fun AppDialog(
    alertType: AlertType,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit = {},
    onDismissButton: () -> Unit = {},
) {
    AlertFactory(
        alertType = alertType,
        onDismissRequest = onDismissRequest,
        onConfirmButton = onConfirmButton,
        onDismissButton = onDismissButton,
    )
}