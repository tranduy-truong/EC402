package com.tranduytruong.novatech.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tranduytruong.novatech.core.domain.model.Product
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.NovaTechBackground
import com.tranduytruong.novatech.ui.components.NovaTechPrimaryButton
import com.tranduytruong.novatech.ui.components.PriceText
import com.tranduytruong.novatech.ui.components.glass.GlassCard
import com.tranduytruong.novatech.ui.components.glass.GlassIconButton
import com.tranduytruong.novatech.ui.components.glass.GlassTopBar
import com.tranduytruong.novatech.ui.theme.GlassTokens
import com.tranduytruong.novatech.ui.theme.RatingYellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, vm: StoreViewModel, product: Product) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isDark = isSystemInDarkTheme()

    NovaTechBackground {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                GlassTopBar(
                    title = "Chi tiết sản phẩm",
                    navigationIcon = {
                        GlassIconButton(
                            icon = Icons.Rounded.ArrowBack,
                            contentDescription = "Quay lại",
                            onClick = { navController.popBackStack() },
                            size = 38.dp,
                            iconSize = 20.dp,
                        )
                    },
                )
            },
            bottomBar = {
                // Floating Bottom Action Bar with glass padding
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                ) {
                    NovaTechPrimaryButton(
                        text = { Text("Thêm vào giỏ hàng") },
                        onClick = {
                            vm.addToCart(product)
                            scope.launch {
                                snackbarHostState.showSnackbar("Đã thêm ${product.name} vào giỏ hàng")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.AddShoppingCart,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                    )
                }
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Product Image Container
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    elevation = GlassTokens.ElevationHigh,
                    contentPadding = PaddingValues(20.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isDark) Color(0xFF0F1E38).copy(alpha = 0.60f)
                                else Color(0xFFEBF1FF).copy(alpha = 0.70f)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(product.imageRes),
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }

                // Main Info Card
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(20.dp),
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = product.category.uppercase(),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                            ),
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Rating & Sales Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = RatingYellow.copy(alpha = 0.16f),
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = null,
                                        tint = RatingYellow,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${product.rating}",
                                        color = RatingYellow,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                    )
                                }
                            }

                            Text(
                                text = "Đã bán ${product.id * 127}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Price formatting
                        PriceText(
                            price = product.price,
                            originalPrice = product.oldPrice,
                            priceFontSize = 26.sp,
                            discountPercentage = product.oldPrice?.let {
                                ((it - product.price) * 100 / it).toInt()
                            },
                        )
                    }
                }

                // Benefits & Guarantees Strip
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        BenefitItem(icon = Icons.Rounded.LocalShipping, label = "Freeship toàn quốc")
                        BenefitItem(icon = Icons.Rounded.Shield, label = "Chính hãng 100%")
                        BenefitItem(icon = Icons.Rounded.CheckCircle, label = "Bảo hành 12T")
                    }
                }

                // Product Description
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(20.dp),
                ) {
                    Column {
                        Text(
                            text = "Mô tả sản phẩm",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = product.description,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp,
                            ),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
private fun BenefitItem(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}
