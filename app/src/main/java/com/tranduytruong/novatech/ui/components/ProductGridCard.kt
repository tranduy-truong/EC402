package com.tranduytruong.novatech.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.LocalShipping
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
fun ProductGridCard(
    product: Product,
    onOpen: () -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = isSystemInDarkTheme()
    val discount = product.oldPrice?.let { ((it - product.price) * 100 / it).toInt() }

    GlassCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onOpen,
        shape = RoundedCornerShape(22.dp),
        elevation = GlassTokens.ElevationMedium,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Product Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(148.dp)
                    .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
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
                        .fillMaxWidth()
                        .height(130.dp)
                        .padding(12.dp),
                    contentScale = ContentScale.Fit,
                )

                // Discount Badge (Top Left)
                if (discount != null && discount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(GlassTokens.primaryGradientBrush())
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                    ) {
                        Text(
                            text = "-$discount%",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            }

            // Product Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Text(
                    text = product.name,
                    modifier = Modifier.height(40.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 19.sp,
                    ),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating & Sold Count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Rating",
                        tint = RatingYellow,
                        modifier = Modifier.size(14.dp),
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

                Spacer(modifier = Modifier.height(8.dp))

                // Price and Add to Cart Action
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        PriceText(
                            price = product.price,
                            originalPrice = product.oldPrice,
                            priceFontSize = 15.sp,
                            showDiscountBadge = false,
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.LocalShipping,
                                contentDescription = "Freeship",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(12.dp),
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Freeship",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Medium,
                                ),
                            )
                        }
                    }

                    // Add to Cart Glass Button
                    GlassIconButton(
                        icon = Icons.Rounded.AddShoppingCart,
                        contentDescription = "Thêm vào giỏ hàng",
                        onClick = onAdd,
                        size = 38.dp,
                        iconSize = 18.dp,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}
