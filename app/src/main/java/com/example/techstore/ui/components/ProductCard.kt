package com.example.techstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techstore.Product
import com.example.techstore.ui.theme.BrandBlue
import com.example.techstore.ui.theme.RatingYellow
import com.example.techstore.ui.theme.SaleRed
import com.example.techstore.util.formatMoney

@Composable
fun ProductCard(product: Product, onOpen: () -> Unit, onAdd: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onOpen)) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit,
            )
            Spacer(Modifier.width(14.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(product.category, color = BrandBlue, fontSize = 12.sp)
                Text(product.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text("★ ${product.rating}", color = RatingYellow, fontSize = 13.sp)
                Spacer(Modifier.height(2.dp))
                Text(formatMoney(product.price), color = SaleRed, fontWeight = FontWeight.Bold)
                product.oldPrice?.let {
                    Text(formatMoney(it), fontSize = 12.sp, textDecoration = TextDecoration.LineThrough)
                }
            }
            IconButton(onClick = onAdd) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Thêm vào giỏ")
            }
        }
    }
}
