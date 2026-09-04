package com.example.techstore.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.techstore.StoreViewModel
import com.example.techstore.ui.theme.AppBackground
import com.example.techstore.ui.theme.SaleRed
import com.example.techstore.util.formatMoney

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, vm: StoreViewModel) {
    Scaffold(topBar = { TopAppBar(title = { Text("Giỏ hàng (${vm.cartCount})", fontWeight = FontWeight.Bold) }) }) { padding ->
        if (vm.cartItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(72.dp), tint = Color.Gray)
                Text("Giỏ hàng đang trống", fontSize = 18.sp)
                Button(onClick = { navController.navigate("home") }) { Text("Tiếp tục mua sắm") }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(vm.cartItems, key = { it.product.id }) { item ->
                    ElevatedCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text(item.product.name, fontWeight = FontWeight.SemiBold)
                            Text(formatMoney(item.product.price), color = SaleRed)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { vm.changeQuantity(item.product.id, -1) }) {
                                    Icon(Icons.Default.Remove, contentDescription = "Giảm")
                                }
                                Text(item.quantity.toString(), fontWeight = FontWeight.Bold)
                                IconButton(onClick = { vm.changeQuantity(item.product.id, 1) }) {
                                    Icon(Icons.Default.Add, contentDescription = "Tăng")
                                }
                                Spacer(Modifier.weight(1f))
                                Text(formatMoney(item.product.price * item.quantity), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                item {
                    ElevatedCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Tổng cộng", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(formatMoney(vm.total), color = SaleRed, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            ) { Text("Tiến hành đặt hàng") }
                        }
                    }
                }
            }
        }
    }
}
