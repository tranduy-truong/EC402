package com.example.techstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.TabletAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techstore.ui.theme.BrandBlue

@Composable
fun CategoryStrip(categories: List<String>, onCategoryClick: (String) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        items(categories) { category ->
            Column(
                modifier = Modifier.size(width = 68.dp, height = 90.dp).clickable { onCategoryClick(category) },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.size(56.dp).background(Color(0xFFEFF6FF), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(categoryIcon(category), contentDescription = category, tint = BrandBlue, modifier = Modifier.size(28.dp))
                }
                Text(category, fontSize = 11.sp, lineHeight = 13.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

private fun categoryIcon(category: String): ImageVector = when (category) {
    "Điện thoại" -> Icons.Default.PhoneAndroid
    "Laptop" -> Icons.Default.Laptop
    "Máy tính bảng" -> Icons.Default.TabletAndroid
    "Phụ kiện" -> Icons.Default.Headphones
    else -> Icons.Default.Apps
}
