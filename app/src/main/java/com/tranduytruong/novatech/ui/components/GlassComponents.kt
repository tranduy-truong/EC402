package com.tranduytruong.novatech.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.backdrop.highlight.Highlight
import com.kyant.backdrop.shadow.Shadow

private val LocalNovaTechBackdrop = staticCompositionLocalOf<Backdrop?> { null }

@Composable
fun NovaTechBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val primaryGlow = colors.primary.copy(alpha = 0.12f)
    val secondaryGlow = colors.secondary.copy(alpha = 0.10f)

    val backdrop = rememberLayerBackdrop()

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .layerBackdrop(backdrop)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colors.background,
                            colors.surfaceVariant.copy(alpha = 0.42f),
                            colors.background,
                        )
                    )
                )
                .drawBehind {
                    drawCircle(
                        color = primaryGlow,
                        radius = size.minDimension * 0.48f,
                        center = Offset(size.width * 1.04f, size.height * 0.08f),
                    )
                    drawCircle(
                        color = secondaryGlow,
                        radius = size.minDimension * 0.38f,
                        center = Offset(-size.width * 0.08f, size.height * 0.76f),
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(colors.tertiary.copy(alpha = 0.12f), Color.Transparent)
                        ),
                        radius = size.minDimension * 0.30f,
                        center = Offset(size.width * 0.70f, size.height * 0.55f),
                    )
                }
        )
        CompositionLocalProvider(LocalNovaTechBackdrop provides backdrop) {
            content()
        }
    }
}

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    backdrop: Backdrop? = LocalNovaTechBackdrop.current,
    surfaceColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.48f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    if (backdrop != null) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Box(
                modifier = modifier.drawBackdrop(
                    backdrop = backdrop,
                    shape = { shape },
                    effects = {
                        vibrancy()
                        blur(8.dp.toPx())
                        lens(
                            refractionHeight = 12.dp.toPx(),
                            refractionAmount = 18.dp.toPx(),
                            depthEffect = true,
                            chromaticAberration = true,
                        )
                    },
                    highlight = {
                        Highlight.Default.copy(
                            width = 0.8.dp,
                            alpha = 0.82f,
                        )
                    },
                    shadow = {
                        Shadow(
                            radius = 18.dp,
                            color = Color.Black.copy(alpha = 0.14f),
                        )
                    },
                    onDrawSurface = { drawRect(surfaceColor) },
                ),
            ) {
                Box(modifier = Modifier.padding(contentPadding), content = content)
            }
        }
    } else {
        Surface(
            modifier = modifier,
            shape = shape,
            color = colors.surface.copy(alpha = 0.82f),
            contentColor = contentColor,
            border = BorderStroke(1.dp, colors.onSurface.copy(alpha = 0.09f)),
            shadowElevation = 8.dp,
            tonalElevation = 0.dp,
        ) {
            Box(modifier = Modifier.padding(contentPadding), content = content)
        }
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
    val shape = RoundedCornerShape(18.dp)
    val containerColor = if (enabled) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.76f)
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    }
    val buttonContentColor = if (enabled) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    GlassSurface(
        modifier = modifier
            .defaultMinSize(minHeight = 54.dp)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        shape = shape,
        surfaceColor = containerColor,
        contentColor = buttonContentColor,
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 54.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                leadingIcon?.let {
                    Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                        it()
                    }
                    Box(modifier = Modifier.width(8.dp))
                }
                text()
            }
        }
    }
}
