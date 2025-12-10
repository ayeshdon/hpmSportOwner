package com.happymesport.merchant.presantation.facility

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.presantation.theme.AppThemePrimary
import com.happymesport.merchant.presantation.theme.LocalSpacing
import com.happymesport.merchant.presantation.theme.LocalTypography
import com.happymesport.merchant.presantation.theme.White

@Composable
fun FacilityItem(
    facility: AvailableFacility,
    isSelected: Boolean,
    onClick: (AvailableFacility) -> Unit,
) {
    val borderColor =
        if (isSelected) {
            AppThemePrimary
        } else {
            White
        }
    Card(
        modifier =
            Modifier
                .width(120.dp)
                .height(120.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .clickable(onClick = { onClick(facility) }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = White,
            ),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(LocalSpacing.current.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(48.dp),
            ) {
                AsyncImage(
                    model = "${facility.icon}",
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier =
                        Modifier
                            .size(48.dp),
                )
            }

            Text(
                text = "${facility.name}",
                style = LocalTypography.current.labelLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${facility.description}",
                style = LocalTypography.current.labelSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
