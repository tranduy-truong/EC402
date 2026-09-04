package com.tranduytruong.novatech.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private data class PromoBanner(
    val title: String,
    val subtitle: String,
    val colors: List<Color>,
)

private val promoBanners = listOf(
    PromoBanner("Siêu sale công nghệ", "Giảm đến 20% • Freeship toàn quốc", listOf(Color(0xFF1D4ED8), Color(0xFF38BDF8))),
    PromoBanner("Điện thoại chính hãng", "Bảo hành 12 tháng • Đổi mới 30 ngày", listOf(Color(0xFF7C3AED), Color(0xFFC084FC))),
    PromoBanner("Laptop cho năm học mới", "Trả góp 0% • Tặng bộ phụ kiện", listOf(Color(0xFFEA580C), Color(0xFFFBBF24))),
)

@Composable
fun BannerCarousel() {
    val pagerState = rememberPagerState(pageCount = { promoBanners.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3_500)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % promoBanners.size)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 12.dp,
        ) { page ->
            val banner = promoBanners[page]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Brush.horizontalGradient(banner.colors), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.CenterStart,
            ) {
                Column(Modifier.padding(22.dp)) {
                    Text(banner.title, color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    Text(banner.subtitle, color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                }
            }
        }
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            repeat(promoBanners.size) { index ->
                Box(
                    Modifier
                        .size(if (index == pagerState.currentPage) 9.dp else 7.dp)
                        .background(
                            if (index == pagerState.currentPage) Color(0xFF2563EB) else Color(0xFFCBD5E1),
                            CircleShape,
                        )
                )
            }
        }
    }
}
