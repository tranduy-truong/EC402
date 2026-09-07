package com.tranduytruong.novatech.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.LocalOffer
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranduytruong.novatech.ui.theme.AnimationTokens
import com.tranduytruong.novatech.ui.theme.GlassTokens
import kotlinx.coroutines.delay

private data class PromoBanner(
    val title: String,
    val subtitle: String,
    val tag: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
)

private val promoBanners = listOf(
    PromoBanner(
        title = "Siêu Sale Công Nghệ",
        subtitle = "Giảm đến 20% • Freeship toàn quốc",
        tag = "HOT DEAL",
        icon = Icons.Rounded.ElectricBolt,
        gradientColors = listOf(Color(0xFF0F4C81), Color(0xFF1E6EF7), Color(0xFF0EA5E9)),
    ),
    PromoBanner(
        title = "Điện Thoại Chính Hãng",
        subtitle = "Bảo hành 12 tháng • Đổi mới 30 ngày",
        tag = "CHÍNH HÃNG",
        icon = Icons.Rounded.LocalOffer,
        gradientColors = listOf(Color(0xFF4C1D95), Color(0xFF7C3AED), Color(0xFFC084FC)),
    ),
    PromoBanner(
        title = "Laptop Mùa Khai Trường",
        subtitle = "Trả góp 0% • Tặng bộ phụ kiện cao cấp",
        tag = "ƯU ĐÃI HỌC SINH",
        icon = Icons.Rounded.School,
        gradientColors = listOf(Color(0xFF065F46), Color(0xFF059669), Color(0xFF34D399)),
    ),
)

@Composable
fun BannerCarousel(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { promoBanners.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4_000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % promoBanners.size)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 14.dp,
        ) { page ->
            val banner = promoBanners[page]
            val shape = RoundedCornerShape(24.dp)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .shadow(
                        elevation = GlassTokens.ElevationMedium,
                        shape = shape,
                        clip = false,
                        spotColor = banner.gradientColors.first().copy(alpha = 0.40f),
                    )
                    .border(
                        border = BorderStroke(
                            width = GlassTokens.BorderThin,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.60f),
                                    Color.White.copy(alpha = 0.15f),
                                )
                            ),
                        ),
                        shape = shape,
                    ),
                shape = shape,
                color = Color.Transparent,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.horizontalGradient(banner.gradientColors))
                        .padding(20.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(140.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.08f),
                                shape = CircleShape,
                            )
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.22f))
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = banner.icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = banner.tag,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.8.sp,
                                )
                            }
                        }

                        Column {
                            Text(
                                text = banner.title,
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 22.sp,
                                ),
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = banner.subtitle,
                                color = Color.White.copy(alpha = 0.90f),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 13.sp,
                                ),
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "Khám phá ngay",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(promoBanners.size) { index ->
                val isSelected = index == pagerState.currentPage
                val width by animateDpAsState(
                    targetValue = if (isSelected) 24.dp else 8.dp,
                    animationSpec = AnimationTokens.SpringFastDp,
                    label = "indicatorWidth",
                )

                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(width)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
                        )
                )
            }
        }
    }
}
