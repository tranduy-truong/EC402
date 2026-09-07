package com.tranduytruong.novatech.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.EmptyState
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.ProductCard
import com.tranduytruong.novatech.ui.components.glass.GlassChip
import com.tranduytruong.novatech.ui.components.glass.GlassTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController, vm: StoreViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val products = vm.products

    NovaTechBackground {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                GlassTopBar(
                    title = "Danh mục sản phẩm",
                    subtitle = vm.selectedCategory,
                )
            },
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                // Category Selector Filter Chips
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(vertical = 4.dp),
                    ) {
                        items(vm.categories) { category ->
                            GlassChip(
                                text = category,
                                isSelected = vm.selectedCategory == category,
                                onClick = { vm.selectedCategory = category },
                            )
                        }
                    }
                }

                if (products.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Rounded.Inventory2,
                            title = "Danh mục đang trống",
                            message = "Chưa có sản phẩm phù hợp với bộ lọc hiện tại.",
                            actionLabel = "Xem tất cả",
                            onAction = { vm.selectedCategory = "Tất cả" },
                        )
                    }
                } else {
                    items(products, key = { it.id }) { product ->
                        ProductCard(
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
