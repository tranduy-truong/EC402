package com.tranduytruong.novatech.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BrandBlue = Color(0xFF2563EB)
val AppBackground = Color(0xFFF8FAFC)
val SaleRed = Color(0xFFDC2626)
val RatingYellow = Color(0xFFF59E0B)

private val NovaTechColors = lightColorScheme(
    primary = BrandBlue,
    background = AppBackground,
    surface = Color.White,
    secondary = Color(0xFF0EA5E9),
)

@Composable
fun NovaTechTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = NovaTechColors, content = content)
}
