package com.tranduytruong.novatech.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranduytruong.novatech.ui.theme.GlassTokens
import kotlinx.coroutines.delay

@Composable
fun FlashSaleHeader(modifier: Modifier = Modifier) {
    var remainingSeconds by rememberSaveable { mutableLongStateOf(8_325L) }

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1_000)
            remainingSeconds--
        }
    }

    val hours = remainingSeconds / 3_600
    val minutes = (remainingSeconds % 3_600) / 60
    val seconds = remainingSeconds % 60

    val isDark = isSystemInDarkTheme()
    val shape = RoundedCornerShape(18.dp)
    val glassBg = GlassTokens.glassCardBrush(isDark = isDark, alphaFraction = 0.70f)
    val borderBrush = GlassTokens.glassBorderBrush(isDark = isDark)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = GlassTokens.ElevationLow,
                shape = shape,
                clip = false,
            )
            .border(
                border = BorderStroke(GlassTokens.BorderThin, borderBrush),
                shape = shape,
            ),
        shape = shape,
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .background(glassBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Flash Sale Title Tag
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(GlassTokens.primaryGradientBrush(), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ElectricBolt,
                        contentDescription = "Flash Sale",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "FLASH SALE",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.5.sp,
                    ),
                )
            }

            // Countdown Timer Boxes
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TimeBox(hours)
                Text(
                    text = ":",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
                TimeBox(minutes)
                Text(
                    text = ":",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
                TimeBox(seconds)
            }
        }
    }
}

@Composable
private fun TimeBox(value: Long) {
    Box(
        modifier = Modifier
            .background(
                brush = GlassTokens.primaryGradientBrush(),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 8.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
