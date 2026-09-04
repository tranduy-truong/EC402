package com.example.techstore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.techstore.StoreViewModel
import com.example.techstore.ui.components.ProductCard
import com.example.techstore.ui.theme.AppBackground
import com.example.techstore.ui.theme.BrandBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: StoreViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TechStore", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            Brush.horizontalGradient(listOf(BrandBlue, Color(0xFF38BDF8)))
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("Siêu sale công nghệ", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("Giảm giá đến 20%", color = Color.White, fontSize = 17.sp)
                        Text("Giao hàng toàn quốc", color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
            item {
                OutlinedTextField(
                    value = vm.query,
                    onValueChange = { vm.query = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Tìm điện thoại, laptop...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                )
            }
            item {
                Text("Sản phẩm nổi bật", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            items(vm.products, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    onOpen = { navController.navigate("detail/${product.id}") },
                    onAdd = { vm.addToCart(product) },
                )
            }
            if (vm.products.isEmpty()) {
                item {
                    Spacer(Modifier.height(24.dp))
                    Text("Không tìm thấy sản phẩm phù hợp.")
                }
            }
        }
    }
}
