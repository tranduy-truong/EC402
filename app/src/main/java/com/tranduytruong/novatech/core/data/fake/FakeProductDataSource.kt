package com.tranduytruong.novatech.core.data.fake

import com.tranduytruong.novatech.R
import com.tranduytruong.novatech.core.domain.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeProductDataSource @Inject constructor() {
    val products = listOf(
        Product(
            id = 1,
            name = "iPhone 16 Pro Max",
            category = "Điện thoại",
            imageRes = R.drawable.product_iphone,
            price = 34_990_000,
            oldPrice = 37_990_000,
            rating = 4.9,
            description = "Màn hình Super Retina XDR, hiệu năng mạnh mẽ và hệ thống camera chuyên nghiệp.",
        ),
        Product(
            id = 2,
            name = "Samsung Galaxy S25 Ultra",
            category = "Điện thoại",
            imageRes = R.drawable.product_samsung,
            price = 31_990_000,
            oldPrice = 33_990_000,
            rating = 4.8,
            description = "Điện thoại cao cấp với bút S Pen, camera độ phân giải cao và Galaxy AI.",
        ),
        Product(
            id = 3,
            name = "MacBook Air M4 13 inch",
            category = "Laptop",
            imageRes = R.drawable.product_macbook,
            price = 26_990_000,
            oldPrice = 28_990_000,
            rating = 4.9,
            description = "Laptop mỏng nhẹ, pin lâu, màn hình Liquid Retina và chip Apple Silicon.",
        ),
        Product(
            id = 4,
            name = "ASUS Vivobook 15",
            category = "Laptop",
            imageRes = R.drawable.product_laptop,
            price = 16_490_000,
            oldPrice = 18_490_000,
            rating = 4.6,
            description = "Laptop học tập và văn phòng với màn hình lớn, bàn phím thoải mái.",
        ),
        Product(
            id = 5,
            name = "iPad Air M3",
            category = "Máy tính bảng",
            imageRes = R.drawable.product_tablet,
            price = 17_490_000,
            rating = 4.8,
            description = "Máy tính bảng linh hoạt cho học tập, sáng tạo và giải trí.",
        ),
        Product(
            id = 6,
            name = "Sony WH-1000XM5",
            category = "Phụ kiện",
            imageRes = R.drawable.product_headphones,
            price = 7_490_000,
            oldPrice = 8_490_000,
            rating = 4.7,
            description = "Tai nghe chống ồn chủ động, âm thanh chi tiết và thời lượng pin dài.",
        ),
    )
}
