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

// Arctic Glass Palette
val BrandBlue = Color(0xFF1E6EF7)
val BrandCyan = Color(0xFF0EA5E9)
val BrandPurple = Color(0xFF8B5CF6)
val SaleRed = Color(0xFFFF4D6D)
val RatingYellow = Color(0xFFFFB020)
val SuccessGreen = Color(0xFF10B981)

// Glass Overlay Colors
val GlassLight = Color(0xB8FFFFFF)       // 72% opacity white
val GlassDark = Color(0xB30D1B38)        // 70% opacity deep navy
val GlassBorderLight = Color(0xD9FFFFFF)  // 85% opacity white border
val GlassBorderDark = Color(0x2E63B3FF)   // 18% blue border

private val LightColors = lightColorScheme(
    primary = BrandBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0EDFF),
    onPrimaryContainer = Color(0xFF00388A),
    secondary = BrandCyan,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0369A1),
    tertiary = BrandPurple,
    onTertiary = Color.White,
    background = Color(0xFFF0F4FF),
    onBackground = Color(0xFF0A1628),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0A1628),
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF4A6FA5),
    outline = Color(0xFF94A3B8),
    outlineVariant = Color(0xFFCBD5E1),
    error = Color(0xFFEF4444),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF5BA4FF),
    onPrimary = Color(0xFF002B66),
    primaryContainer = Color(0xFF004499),
    onPrimaryContainer = Color(0xFFD6E4FF),
    secondary = Color(0xFF38BDF8),
    onSecondary = Color(0xFF00354E),
    secondaryContainer = Color(0xFF004D73),
    onSecondaryContainer = Color(0xFFC8EBFD),
    tertiary = Color(0xFFC084FC),
    onTertiary = Color(0xFF3B0764),
    background = Color(0xFF04091A),
    onBackground = Color(0xFFEEF2FF),
    surface = Color(0xFF0D1B38),
    onSurface = Color(0xFFEEF2FF),
    surfaceVariant = Color(0xFF16294D),
    onSurfaceVariant = Color(0xFF7EA9D3),
    outline = Color(0xFF475569),
    outlineVariant = Color(0xFF1E293B),
    error = Color(0xFFF87171),
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

