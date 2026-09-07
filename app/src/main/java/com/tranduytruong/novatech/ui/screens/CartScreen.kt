package com.tranduytruong.novatech.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.EmptyState
import com.tranduytruong.novatech.ui.components.GlassSurface
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.NovaTechPrimaryButton
import com.tranduytruong.novatech.ui.theme.SaleRed
import com.tranduytruong.novatech.util.formatMoney

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, vm: StoreViewModel) {
    NovaTechBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                GlassSurface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                ) {
                    TopAppBar(
                        title = {
                            Column {
                                Text("Giỏ hàng", style = MaterialTheme.typography.headlineSmall)
                                Text(
                                    "${vm.cartCount} sản phẩm đã chọn",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    )
                }
            },
        ) { padding ->
            if (vm.cartItems.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.ShoppingBag,
                    title = "Giỏ hàng đang trống",
                    message = "Khám phá sản phẩm công nghệ mới và thêm món đồ bạn yêu thích.",
                    modifier = Modifier.padding(padding).padding(16.dp).align(Alignment.Center),
                    actionLabel = "Tiếp tục mua sắm",
                    onAction = { navController.navigate("home") },
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(vm.cartItems, key = { it.product.id }) { item ->
                        GlassSurface(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(14.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                            ) {
                                Surface(
                                    modifier = Modifier.size(82.dp),
                                    shape = RoundedCornerShape(18.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                                ) {
                                    Image(
                                        painter = painterResource(item.product.imageRes),
                                        contentDescription = item.product.name,
                                        modifier = Modifier.padding(8.dp),
                                        contentScale = ContentScale.Fit,
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        item.product.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 2,
                                    )
                                    Text(
                                        formatMoney(item.product.price),
                                        color = SaleRed,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Row(
                                        modifier = Modifier.padding(top = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f)) {
                                            IconButton(
                                                onClick = { vm.changeQuantity(item.product.id, -1) },
                                                modifier = Modifier.size(34.dp),
                                            ) {
                                                Icon(Icons.Default.Remove, contentDescription = "Giảm", modifier = Modifier.size(17.dp))
                                            }
                                        }
                                        Text(
                                            item.quantity.toString(),
                                            modifier = Modifier.padding(horizontal = 14.dp),
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
                                            IconButton(
                                                onClick = { vm.changeQuantity(item.product.id, 1) },
                                                modifier = Modifier.size(34.dp),
                                            ) {
                                                Icon(Icons.Default.Add, contentDescription = "Tăng", modifier = Modifier.size(17.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        GlassSurface(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(18.dp),
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text("Tổng cộng", style = MaterialTheme.typography.titleLarge)
                                    Text(
                                        formatMoney(vm.total),
                                        color = SaleRed,
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                }
                                Text(
                                    "Đã bao gồm VAT • Miễn phí vận chuyển",
                                    modifier = Modifier.padding(top = 4.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                NovaTechPrimaryButton(
                                    text = { Text("Tiến hành đặt hàng") },
                                    onClick = { },
                                    modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
