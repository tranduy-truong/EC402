package com.tranduytruong.novatech.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.DesktopWindows
import androidx.compose.material.icons.rounded.Gamepad
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.Laptop
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material.icons.rounded.TabletAndroid
import androidx.compose.material.icons.rounded.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranduytruong.novatech.ui.theme.AnimationTokens
import com.tranduytruong.novatech.ui.theme.GlassTokens

@Composable
fun CategoryStrip(
    categories: List<String>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedCategory: String? = null,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategoryClick(category) },
            )
        }
    }
}

@Composable
private fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val isDark = isSystemInDarkTheme()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1.0f,
        animationSpec = AnimationTokens.SpringFast,
        label = "categoryScale",
    )

    val iconShape = RoundedCornerShape(20.dp)
    val glassBg = GlassTokens.glassCardBrush(isDark = isDark, alphaFraction = 0.70f)
    val borderBrush = if (isSelected) {
        GlassTokens.primaryGradientBrush()
    } else {
        GlassTokens.glassBorderBrush(isDark = isDark)
    }

    Column(
        modifier = Modifier
            .width(72.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = if (isSelected) GlassTokens.ElevationMedium else GlassTokens.ElevationLow,
                    shape = iconShape,
                    clip = false,
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.30f),
                )
                .border(
                    border = BorderStroke(
                        width = if (isSelected) GlassTokens.BorderMedium else GlassTokens.BorderThin,
                        brush = borderBrush,
                    ),
                    shape = iconShape,
                ),
            shape = iconShape,
            color = Color.Transparent,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isSelected) GlassTokens.primaryGradientBrush()
                        else glassBg
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = categoryIcon(category),
                    contentDescription = category,
                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = category,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
    }
}

private fun categoryIcon(category: String): ImageVector = when (category) {
    "Điện thoại" -> Icons.Rounded.PhoneAndroid
    "Laptop" -> Icons.Rounded.Laptop
    "Máy tính bảng" -> Icons.Rounded.TabletAndroid
    "Tai nghe & Loa", "Phụ kiện" -> Icons.Rounded.Headphones
    "Đồng hồ" -> Icons.Rounded.Watch
    "PC & Linh kiện" -> Icons.Rounded.DesktopWindows
    "Gaming" -> Icons.Rounded.Gamepad
    else -> Icons.Rounded.Apps
}
