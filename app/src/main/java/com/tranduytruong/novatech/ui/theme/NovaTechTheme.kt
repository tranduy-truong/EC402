package com.tranduytruong.novatech.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.tranduytruong.novatech.core.domain.model.ThemeMode

val BrandBlue = Color(0xFF4F6BFF)
val BrandPurple = Color(0xFF8B5CF6)
val SaleRed = Color(0xFFFF4D6D)
val RatingYellow = Color(0xFFFFB020)
val SuccessGreen = Color(0xFF22A06B)

private val LightColors = lightColorScheme(
    primary = BrandBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3E8FF),
    onPrimaryContainer = Color(0xFF10236D),
    secondary = BrandPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF0E7FF),
    onSecondaryContainer = Color(0xFF3B176B),
    tertiary = Color(0xFF00A6A6),
    background = Color(0xFFF5F7FF),
    onBackground = Color(0xFF14182B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF181B2F),
    surfaceVariant = Color(0xFFE9ECF7),
    onSurfaceVariant = Color(0xFF5E6378),
    outline = Color(0xFF858AA0),
    outlineVariant = Color(0xFFD7DAE6),
    error = Color(0xFFBA1A1A),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB9C3FF),
    onPrimary = Color(0xFF10236D),
    primaryContainer = Color(0xFF283C91),
    onPrimaryContainer = Color(0xFFE1E6FF),
    secondary = Color(0xFFD7BAFF),
    onSecondary = Color(0xFF43206F),
    secondaryContainer = Color(0xFF39215B),
    onSecondaryContainer = Color(0xFFEBD9FF),
    tertiary = Color(0xFF71D7D5),
    background = Color(0xFF0C1020),
    onBackground = Color(0xFFE7E9F5),
    surface = Color(0xFF171B2E),
    onSurface = Color(0xFFE7E9F5),
    surfaceVariant = Color(0xFF252A40),
    onSurfaceVariant = Color(0xFFC3C6D7),
    outline = Color(0xFF8C90A5),
    outlineVariant = Color(0xFF3B4057),
    error = Color(0xFFFFB4AB),
)

private val NovaTechTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
)

private val NovaTechShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

@Composable
fun NovaTechTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    val colors = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            @Suppress("DEPRECATION")
            window.statusBarColor = colors.background.toArgb()
            @Suppress("DEPRECATION")
            window.navigationBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = NovaTechTypography,
        shapes = NovaTechShapes,
        content = content,
    )
}
