package com.example.techstore.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.techstore.Product
import com.example.techstore.StoreViewModel
import com.example.techstore.ui.theme.AppBackground
import com.example.techstore.ui.theme.BrandBlue
import com.example.techstore.ui.theme.RatingYellow
import com.example.techstore.ui.theme.SaleRed
import com.example.techstore.util.formatMoney

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, vm: StoreViewModel, product: Product) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết sản phẩm") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
            )
        },
        bottomBar = {
            Button(
                onClick = { vm.addToCart(product) },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(52.dp),
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                Text("  Thêm vào giỏ hàng")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding).padding(20.dp)
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.fillMaxWidth().height(260.dp).background(Color.White),
                contentScale = ContentScale.Fit,
            )
            Spacer(Modifier.height(20.dp))
            Text(product.category, color = BrandBlue)
            Text(product.name, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("★ ${product.rating}", color = RatingYellow)
            Spacer(Modifier.height(8.dp))
            Text(formatMoney(product.price), fontSize = 23.sp, color = SaleRed, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(18.dp))
            Text("Mô tả sản phẩm", fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
            Text(product.description, lineHeight = 24.sp)
        }
    }
}
