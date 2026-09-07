package com.tranduytruong.novatech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranduytruong.novatech.core.domain.model.Product
import com.tranduytruong.novatech.ui.components.glass.GlassCard
import com.tranduytruong.novatech.ui.components.glass.GlassIconButton
import com.tranduytruong.novatech.ui.theme.GlassTokens
import com.tranduytruong.novatech.ui.theme.RatingYellow

@Composable
fun ProductCard(
    product: Product,
    onOpen: () -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = isSystemInDarkTheme()

    GlassCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onOpen,
        contentPadding = PaddingValues(12.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = GlassTokens.ElevationMedium,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Product Thumbnail
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isDark) Color(0xFF0F1E38).copy(alpha = 0.60f)
                        else Color(0xFFEBF1FF).copy(alpha = 0.70f)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentScale = ContentScale.Fit,
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = product.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                    ),
                )

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Rating",
                        tint = RatingYellow,
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${product.rating}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    Text(
                        text = "  •  Đã bán ${product.id * 127}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                PriceText(
                    price = product.price,
                    originalPrice = product.oldPrice,
                    priceFontSize = 15.sp,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Add to Cart Button
            GlassIconButton(
                icon = Icons.Rounded.AddShoppingCart,
                contentDescription = "Thêm vào giỏ",
                onClick = onAdd,
                size = 40.dp,
                iconSize = 18.dp,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
