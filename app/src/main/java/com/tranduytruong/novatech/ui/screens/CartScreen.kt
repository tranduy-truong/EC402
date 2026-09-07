package com.tranduytruong.novatech.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.EmptyState
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.NovaTechPrimaryButton
import com.tranduytruong.novatech.ui.components.PriceText
import com.tranduytruong.novatech.ui.components.QuantityStepper
import com.tranduytruong.novatech.ui.components.glass.GlassCard
import com.tranduytruong.novatech.ui.components.glass.GlassTopBar
import com.tranduytruong.novatech.ui.theme.GlassTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, vm: StoreViewModel) {
    val isDark = isSystemInDarkTheme()

    NovaTechBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                GlassTopBar(
                    title = "Giỏ hàng của bạn",
                    subtitle = if (vm.cartCount > 0) "${vm.cartCount} sản phẩm" else null,
                )
            },
        ) { padding ->
            if (vm.cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    EmptyState(
                        icon = Icons.Rounded.ShoppingBag,
                        title = "Giỏ hàng đang trống",
                        message = "Khám phá sản phẩm công nghệ mới và thêm món đồ bạn yêu thích.",
                        actionLabel = "Tiếp tục mua sắm",
                        onAction = { navController.navigate("home") },
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    // Cart Product List
                    items(vm.cartItems, key = { it.product.id }) { item ->
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            contentPadding = PaddingValues(14.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                // Image Box
                                Box(
                                    modifier = Modifier
                                        .size(86.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            if (isDark) Color(0xFF0F1E38).copy(alpha = 0.60f)
                                            else Color(0xFFEBF1FF).copy(alpha = 0.70f)
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Image(
                                        painter = painterResource(item.product.imageRes),
                                        contentDescription = item.product.name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        contentScale = ContentScale.Fit,
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Text(
                                        text = item.product.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 15.sp,
                                        ),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                    )

                                    PriceText(
                                        price = item.product.price,
                                        priceFontSize = 15.sp,
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))

                                    QuantityStepper(
                                        quantity = item.quantity,
                                        onQuantityChange = { newQty ->
                                            val diff = newQty - item.quantity
                                            vm.changeQuantity(item.product.id, diff)
                                        },
                                    )
                                }
                            }
                        }
                    }

                    // Order Summary Box
                    item {
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            elevation = GlassTokens.ElevationHigh,
                            contentPadding = PaddingValues(20.dp),
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Tóm tắt đơn hàng",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    ),
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Tạm tính",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        ),
                                    )
                                    PriceText(
                                        price = vm.total,
                                        priceFontSize = 15.sp,
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Phí vận chuyển",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        ),
                                    )
                                    Text(
                                        text = "Miễn phí",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary,
                                        ),
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Tổng cộng",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        ),
                                    )

                                    PriceText(
                                        price = vm.total,
                                        priceFontSize = 22.sp,
                                    )
                                }

                                Text(
                                    text = "Đã bao gồm VAT • Bảo hành chính hãng",
                                    modifier = Modifier.padding(top = 4.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall,
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                NovaTechPrimaryButton(
                                    text = { Text("Tiến hành đặt hàng") },
                                    onClick = { },
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
