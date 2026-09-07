package com.tranduytruong.novatech.ui.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.BannerCarousel
import com.tranduytruong.novatech.ui.components.CategoryStrip
import com.tranduytruong.novatech.ui.components.EmptyState
import com.tranduytruong.novatech.ui.components.FlashSaleHeader
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.ProductGridCard
import com.tranduytruong.novatech.ui.components.SectionHeader
import com.tranduytruong.novatech.ui.components.glass.GlassBadge
import com.tranduytruong.novatech.ui.components.glass.GlassIconButton
import com.tranduytruong.novatech.ui.components.glass.GlassTextField
import com.tranduytruong.novatech.ui.theme.GlassTokens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: StoreViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val products = vm.products

    NovaTechBackground {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                // Liquid Glass Header with Branding & Search
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = "NovaTech",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 24.sp,
                                ),
                            )
                            Text(
                                text = "Công nghệ dẫn lối tương lai",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            GlassIconButton(
                                icon = Icons.Rounded.Notifications,
                                contentDescription = "Thông báo",
                                onClick = { },
                                size = 42.dp,
                                iconSize = 20.dp,
                            )

                            Box {
                                GlassIconButton(
                                    icon = Icons.Rounded.ShoppingCart,
                                    contentDescription = "Giỏ hàng",
                                    onClick = { navController.navigate("cart") },
                                    size = 42.dp,
                                    iconSize = 20.dp,
                                )

                                GlassBadge(
                                    count = vm.cartCount,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 2.dp, end = 2.dp),
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Liquid Glass Search Input
                    GlassTextField(
                        value = vm.query,
                        onValueChange = { vm.query = it },
                        placeholderText = "Bạn muốn tìm sản phẩm gì?",
                    )
                }
            },
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, bottom = 100.dp, top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                if (vm.query.isBlank()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        BannerCarousel()
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionHeader(
                            title = "Danh mục nổi bật",
                            subtitle = "Khám phá thế giới công nghệ",
                            onSeeAllClick = { navController.navigate("categories") },
                        )
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CategoryStrip(
                            categories = vm.categories,
                            onCategoryClick = { category ->
                                vm.selectedCategory = category
                                navController.navigate("categories")
                            },
                        )
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        FlashSaleHeader()
                    }
                } else {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionHeader(
                            title = "Kết quả tìm kiếm",
                            subtitle = "Tìm thấy ${products.size} sản phẩm",
                        )
                    }
                }

                if (products.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        EmptyState(
                            icon = Icons.Rounded.SearchOff,
                            title = "Không tìm thấy sản phẩm",
                            message = "Thử từ khóa khác hoặc xóa bộ lọc hiện tại.",
                            actionLabel = "Xóa bộ lọc",
                            onAction = {
                                vm.query = ""
                                vm.selectedCategory = "Tất cả"
                            },
                        )
                    }
                } else {
                    items(products, key = { it.id }) { product ->
                        ProductGridCard(
                            product = product,
                            onOpen = { navController.navigate("detail/${product.id}") },
                            onAdd = {
                                vm.addToCart(product)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Đã thêm ${product.name} vào giỏ")
                                }
                            },
                        )
                    }
                }

                if (vm.query.isBlank() && products.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionHeader(
                            title = "Gợi ý hôm nay",
                            subtitle = "Dành riêng cho bạn",
                        )
                    }

                    items(products.reversed(), key = { "suggested-${it.id}" }) { product ->
                        ProductGridCard(
                            product = product,
                            onOpen = { navController.navigate("detail/${product.id}") },
                            onAdd = {
                                vm.addToCart(product)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Đã thêm ${product.name} vào giỏ")
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
