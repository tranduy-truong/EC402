package com.tranduytruong.novatech.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.EmptyState
import com.tranduytruong.novatech.ui.components.GlassSurface
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController, vm: StoreViewModel) {
    val products = vm.products

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
                            Text("Danh mục", style = MaterialTheme.typography.headlineSmall)
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    )
                }
            },
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(vm.categories) { category ->
                            FilterChip(
                                selected = vm.selectedCategory == category,
                                onClick = { vm.selectedCategory = category },
                                label = { Text(category) },
                            )
                        }
                    }
                }

                if (products.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Default.Inventory2,
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
                            onAdd = { vm.addToCart(product) },
                        )
                    }
                }
            }
        }
    }
}
