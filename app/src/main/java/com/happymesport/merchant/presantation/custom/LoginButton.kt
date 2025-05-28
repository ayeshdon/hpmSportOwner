package com.happymesport.merchant.presantation.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.R
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.white

@Composable
fun LoginRoundedButton(
    enable: Boolean = false,
    onClick: () -> Unit,
    imageResource: Int?,
    buttonText: String,
    backgroundColor: Color = Color(0x1FFFFFFF),
) {
    Button(
        enabled = enable,
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(16.dp),
        shape =
            RoundedCornerShape(20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            imageResource?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = "Button Icon",
                    modifier = Modifier.width(50.dp).height(50.dp),
                )
            }
            Text(
                text = buttonText,
                color = MaterialTheme.colorScheme.white,
                style = LocalTypography.current.titleMedium,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomRoundedButton() {
    LoginRoundedButton(
        enable = true,
        onClick = { },
        imageResource = R.drawable.img_login_google,
        buttonText = "Custom Button",
    )
}
