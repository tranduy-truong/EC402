package com.example.techstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.techstore.Product
import com.example.techstore.ui.theme.BrandBlue
import com.example.techstore.ui.theme.RatingYellow
import com.example.techstore.ui.theme.SaleRed
import com.example.techstore.util.formatMoney

@Composable
fun ProductGridCard(product: Product, onOpen: () -> Unit, onAdd: () -> Unit) {
    val discount = product.oldPrice?.let { ((it - product.price) * 100 / it).toInt() }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onOpen),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(145.dp).background(Color(0xFFF8FAFC))) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxWidth().height(145.dp).padding(8.dp),
                    contentScale = ContentScale.Fit,
                )
                discount?.let {
                    Text(
                        text = "-$it%",
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(SaleRed)
                            .padding(horizontal = 7.dp, vertical = 3.dp),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Column(Modifier.padding(10.dp)) {
                Text(
                    product.name,
                    modifier = Modifier.height(42.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.Medium,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("★ ${product.rating}", color = RatingYellow, fontSize = 11.sp)
                    Text("  |  Đã bán ${product.id * 127}", color = Color.Gray, fontSize = 10.sp)
                }
                Spacer(Modifier.height(5.dp))
                Text(formatMoney(product.price), color = SaleRed, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        product.oldPrice?.let {
                            Text(formatMoney(it), color = Color.Gray, fontSize = 10.sp, textDecoration = TextDecoration.LineThrough)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = BrandBlue, modifier = Modifier.size(13.dp))
                            Text(" Freeship", color = BrandBlue, fontSize = 10.sp)
                        }
                    }
                    FilledIconButton(
                        onClick = onAdd,
                        modifier = Modifier.size(34.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = BrandBlue),
                    ) {
                        Icon(Icons.Default.AddShoppingCart, contentDescription = "Thêm vào giỏ", modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}
