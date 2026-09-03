package com.example.techstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.text.NumberFormat
import java.util.Locale

private val Brand = Color(0xFF2563EB)
private val Background = Color(0xFFF8FAFC)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = Brand, background = Background)) {
                TechStoreApp()
            }
        }
    }
}

@Composable
fun TechStoreApp(vm: StoreViewModel = viewModel()) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") { HomeScreen(nav, vm) }
        composable("cart") { CartScreen(nav, vm) }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry -> vm.product(entry.arguments?.getInt("id") ?: 0)?.let { DetailScreen(nav, vm, it) } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController, vm: StoreViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TechStore", fontWeight = FontWeight.Bold) },
                actions = { CartButton(vm.cartCount) { nav.navigate("cart") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Background).padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("Công nghệ mới, giá tốt mỗi ngày", fontSize = 23.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = vm.query,
                    onValueChange = { vm.query = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Tìm điện thoại, laptop...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    singleLine = true
                )
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(vm.categories) { category ->
                        FilterChip(
                            selected = vm.selectedCategory == category,
                            onClick = { vm.selectedCategory = category },
                            label = { Text(category) }
                        )
                    }
                }
            }
            item { Text("Sản phẩm nổi bật", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
            items(vm.products, key = { it.id }) { product ->
                ProductCard(product, { nav.navigate("detail/${product.id}") }) { vm.addToCart(product) }
            }
            if (vm.products.isEmpty()) item { Text("Không tìm thấy sản phẩm phù hợp.") }
        }
    }
}

@Composable
fun ProductCard(product: Product, onOpen: () -> Unit, onAdd: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onOpen)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(92.dp).background(Color(0xFFEFF6FF)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.PhoneAndroid, null, tint = Brand, modifier = Modifier.size(48.dp)) }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(product.category, color = Brand, fontSize = 12.sp)
                Text(product.name, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                Text("★ ${product.rating}", color = Color(0xFFF59E0B), fontSize = 13.sp)
                Text(money(product.price), color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                product.oldPrice?.let { Text(money(it), fontSize = 12.sp, textDecoration = TextDecoration.LineThrough) }
            }
            IconButton(onClick = onAdd) { Icon(Icons.Default.AddShoppingCart, "Thêm vào giỏ") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(nav: NavController, vm: StoreViewModel, product: Product) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết sản phẩm") },
                navigationIcon = { IconButton({ nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) } },
                actions = { CartButton(vm.cartCount) { nav.navigate("cart") } }
            )
        },
        bottomBar = {
            Button(
                onClick = { vm.addToCart(product) },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(52.dp)
            ) { Icon(Icons.Default.AddShoppingCart, null); Spacer(Modifier.width(8.dp)); Text("Thêm vào giỏ hàng") }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            Box(
                Modifier.fillMaxWidth().height(240.dp).background(Color(0xFFEFF6FF)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Devices, null, tint = Brand, modifier = Modifier.size(110.dp)) }
            Spacer(Modifier.height(20.dp))
            Text(product.category, color = Brand)
            Text(product.name, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("★ ${product.rating}", color = Color(0xFFF59E0B))
            Spacer(Modifier.height(8.dp))
            Text(money(product.price), fontSize = 24.sp, color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(18.dp))
            Text("Mô tả", fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
            Text(product.description, lineHeight = 24.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(nav: NavController, vm: StoreViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Giỏ hàng (${vm.cartCount})") },
                navigationIcon = { IconButton({ nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) } }
            )
        },
        bottomBar = {
            if (vm.cartItems.isNotEmpty()) Surface(shadowElevation = 8.dp) {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Tổng cộng", fontWeight = FontWeight.Bold)
                        Text(money(vm.total), color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) { Text("Đặt hàng") }
                }
            }
        }
    ) { padding ->
        if (vm.cartItems.isEmpty()) {
            Column(
                Modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.ShoppingCart, null, modifier = Modifier.size(72.dp), tint = Color.Gray)
                Text("Giỏ hàng đang trống")
                TextButton({ nav.popBackStack() }) { Text("Tiếp tục mua sắm") }
            }
        } else LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(vm.cartItems, key = { it.product.id }) { item ->
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(item.product.name, fontWeight = FontWeight.SemiBold)
                        Text(money(item.product.price), color = Color(0xFFDC2626))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton({ vm.changeQuantity(item.product.id, -1) }) { Icon(Icons.Default.Remove, null) }
                            Text(item.quantity.toString(), fontWeight = FontWeight.Bold)
                            IconButton({ vm.changeQuantity(item.product.id, 1) }) { Icon(Icons.Default.Add, null) }
                            Spacer(Modifier.weight(1f))
                            Text(money(item.product.price * item.quantity), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartButton(count: Int, onClick: () -> Unit) {
    BadgedBox(badge = { if (count > 0) Badge { Text(count.toString()) } }) {
        IconButton(onClick) { Icon(Icons.Default.ShoppingCart, "Giỏ hàng") }
    }
}

private fun money(value: Long): String =
    NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(value)
