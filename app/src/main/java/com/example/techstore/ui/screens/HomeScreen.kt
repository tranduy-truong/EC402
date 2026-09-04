package com.example.techstore.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.techstore.StoreViewModel
import com.example.techstore.ui.components.BannerCarousel
import com.example.techstore.ui.components.CategoryStrip
import com.example.techstore.ui.components.FlashSaleHeader
import com.example.techstore.ui.components.ProductGridCard
import com.example.techstore.ui.theme.AppBackground
import com.example.techstore.ui.theme.BrandBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: StoreViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = AppBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Surface(shadowElevation = 3.dp, color = Color.White) {
                Column {
                    TopAppBar(
                        title = {
                            Text("TechStore", color = BrandBlue, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.NotificationsNone, contentDescription = "Thông báo")
                            }
                            BadgedBox(
                                badge = { if (vm.cartCount > 0) Badge { Text(vm.cartCount.toString()) } },
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
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                        placeholder = { Text("Bạn muốn tìm sản phẩm gì?") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BrandBlue) },
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF1F5F9),
                            unfocusedContainerColor = Color(0xFFF1F5F9),
                            focusedBorderColor = BrandBlue,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        singleLine = true,
                    )
                }
            }
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding),
            contentPadding = PaddingValues(14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) { BannerCarousel() }
            item(span = { GridItemSpan(maxLineSpan) }) {
                SectionTitle("Danh mục nổi bật", "Xem tất cả")
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryStrip(vm.categories) { category ->
                    vm.selectedCategory = category
                    navController.navigate("categories")
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) { FlashSaleHeader() }
            items(vm.products, key = { it.id }) { product ->
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
            item(span = { GridItemSpan(maxLineSpan) }) {
                SectionTitle("Gợi ý hôm nay", "Dành cho bạn")
            }
            items(vm.products.reversed(), key = { "suggested-${it.id}" }) { product ->
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

@Composable
private fun SectionTitle(title: String, action: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        Text(action, color = BrandBlue, fontSize = 13.sp)
    }
}
