package com.tranduytruong.novatech.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.BannerCarousel
import com.tranduytruong.novatech.ui.components.CategoryStrip
import com.tranduytruong.novatech.ui.components.EmptyState
import com.tranduytruong.novatech.ui.components.FlashSaleHeader
import com.tranduytruong.novatech.ui.components.GlassSurface
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.ProductGridCard
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
                GlassSurface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                ) {
                    Column {
                        TopAppBar(
                            title = {
                                Column {
                                    Text("NovaTech", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                                    Text(
                                        "Công nghệ dẫn lối tương lai",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                            actions = {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.NotificationsNone, contentDescription = "Thông báo")
                                }
                                BadgedBox(
                                    badge = {
                                        if (vm.cartCount > 0) Badge { Text(vm.cartCount.coerceAtMost(99).toString()) }
                                    },
                                ) {
                                    IconButton(onClick = { navController.navigate("cart") }) {
                                        Icon(Icons.Default.ShoppingCart, contentDescription = "Giỏ hàng")
                                    }
                                }
                            },
                        )
                        OutlinedTextField(
                            value = vm.query,
                            onValueChange = { vm.query = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
                            placeholder = { Text("Bạn muốn tìm sản phẩm gì?") },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.62f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.48f),
                                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f),
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                            ),
                            singleLine = true,
                        )
                    }
                }
            },
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (vm.query.isBlank()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { BannerCarousel() }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionTitle("Danh mục nổi bật", "Khám phá")
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CategoryStrip(vm.categories) { category ->
                            vm.selectedCategory = category
                            navController.navigate("categories")
                        }
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) { FlashSaleHeader() }
                } else {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionTitle("Kết quả tìm kiếm", "${products.size} sản phẩm")
                    }
                }

                if (products.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        EmptyState(
                            icon = Icons.Default.SearchOff,
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
                                scope.launch { snackbarHostState.showSnackbar("Đã thêm ${product.name} vào giỏ") }
                            },
                        )
                    }
                }

                if (vm.query.isBlank() && products.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        SectionTitle("Gợi ý hôm nay", "Dành cho bạn")
                    }
                    items(products.reversed(), key = { "suggested-${it.id}" }) { product ->
                        ProductGridCard(
                            product = product,
                            onOpen = { navController.navigate("detail/${product.id}") },
                            onAdd = {
                                vm.addToCart(product)
                                scope.launch { snackbarHostState.showSnackbar("Đã thêm ${product.name} vào giỏ") }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, action: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Text(action, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
    }
}
