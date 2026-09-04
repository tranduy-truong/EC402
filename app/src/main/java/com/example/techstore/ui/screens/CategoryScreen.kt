package com.example.techstore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.techstore.StoreViewModel
import com.example.techstore.ui.components.ProductCard
import com.example.techstore.ui.theme.AppBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController, vm: StoreViewModel) {
    Scaffold(topBar = { TopAppBar(title = { Text("Danh mục", fontWeight = FontWeight.Bold) }) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding),
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
            items(vm.products, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    onOpen = { navController.navigate("detail/${product.id}") },
                    onAdd = { vm.addToCart(product) },
                )
            }
        }
    }
}
