package com.tranduytruong.novatech.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FlashSaleHeader() {
    var remainingSeconds by rememberSaveable { mutableLongStateOf(8_325L) }

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1_000)
            remainingSeconds--
        }
    }

    val hours = remainingSeconds / 3_600
    val minutes = (remainingSeconds % 3_600) / 60
    val seconds = remainingSeconds % 60

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("FLASH SALE", color = Color(0xFFDC2626), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        Row(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TimeBox(hours)
            Text(":", fontWeight = FontWeight.Bold)
            TimeBox(minutes)
            Text(":", fontWeight = FontWeight.Bold)
            TimeBox(seconds)
        }
    }
}

@Composable
private fun TimeBox(value: Long) {
    Box(
        modifier = Modifier.background(Color(0xFF1E293B), RoundedCornerShape(5.dp)).padding(horizontal = 6.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(value.toString().padStart(2, '0'), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
