package com.tranduytruong.novatech.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.tranduytruong.novatech.ui.theme.AnimationTokens
import com.tranduytruong.novatech.ui.theme.GlassTokens

@Composable
fun NovaTechBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val isDark = isSystemInDarkTheme()
    val colors = MaterialTheme.colorScheme

    val primaryGlow = if (isDark) colors.primary.copy(alpha = 0.18f) else colors.primary.copy(alpha = 0.10f)
    val secondaryGlow = if (isDark) colors.secondary.copy(alpha = 0.15f) else colors.secondary.copy(alpha = 0.08f)
    val topGlow = if (isDark) Color(0xFF1E3A8A).copy(alpha = 0.25f) else Color(0xFFDBEAFE).copy(alpha = 0.50f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        if (isDark) Color(0xFF04091A) else Color(0xFFF0F4FF),
                        if (isDark) Color(0xFF0A152E) else Color(0xFFE8EFFF),
                        if (isDark) Color(0xFF04091A) else Color(0xFFF0F4FF),
                    )
                )
            )
            .drawBehind {
                // Top Light Beam / Glow
                drawCircle(
                    color = topGlow,
                    radius = size.minDimension * 0.65f,
                    center = Offset(size.width * 0.50f, -size.height * 0.05f),
                )
                // Top-right Cyan/Blue Orb
                drawCircle(
                    color = primaryGlow,
                    radius = size.minDimension * 0.52f,
                    center = Offset(size.width * 0.95f, size.height * 0.12f),
                )
                // Bottom-left Indigo/Purple Orb
                drawCircle(
                    color = secondaryGlow,
                    radius = size.minDimension * 0.45f,
                    center = Offset(-size.width * 0.05f, size.height * 0.72f),
                )
            },
        content = content,
    )
}

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    val isDark = isSystemInDarkTheme()
    val glassBg = GlassTokens.glassCardBrush(isDark)
    val borderBrush = GlassTokens.glassBorderBrush(isDark)

    Surface(
        modifier = modifier
            .shadow(
                elevation = GlassTokens.ElevationMedium,
                shape = shape,
                clip = false,
            )
            .border(
                border = BorderStroke(GlassTokens.BorderThin, borderBrush),
                shape = shape,
            ),
        shape = shape,
        color = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
    ) {
        Box(
            modifier = Modifier
                .background(glassBg)
                .padding(contentPadding),
            content = content,
        )
    }
}

@Composable
fun NovaTechPrimaryButton(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.96f else 1.0f,
        animationSpec = AnimationTokens.SpringFast,
        label = "btnScale",
    )

    val shape = RoundedCornerShape(18.dp)

    Surface(
        onClick = onClick,
        modifier = modifier
            .height(54.dp)
            .scale(scale)
            .shadow(
                elevation = if (enabled) 12.dp else 0.dp,
                shape = shape,
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
            ),
        enabled = enabled,
        shape = shape,
        color = Color.Transparent,
        interactionSource = interactionSource,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (enabled) GlassTokens.primaryGradientBrush()
                    else Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.outlineVariant,
                            MaterialTheme.colorScheme.outlineVariant,
                        )
                    )
                )
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                ProvideTextStyle(
                    value = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                    ),
                    content = text,
                )
            }
        }
    }
}

