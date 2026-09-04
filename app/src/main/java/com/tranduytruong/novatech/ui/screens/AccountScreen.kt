package com.tranduytruong.novatech.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranduytruong.novatech.ui.theme.AppBackground
import com.tranduytruong.novatech.ui.theme.BrandBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {
    Scaffold(topBar = { TopAppBar(title = { Text("Tài khoản", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().background(AppBackground).padding(padding).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(96.dp), tint = BrandBlue)
            Text("Chào mừng đến NovaTech", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Đăng nhập để theo dõi đơn hàng và nhận ưu đãi")
            ElevatedCard(Modifier.fillMaxWidth().padding(top = 24.dp)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Đăng nhập") }
                    OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Tạo tài khoản") }
                }
            }
        }
    }
}
