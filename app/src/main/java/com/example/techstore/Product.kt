package com.example.techstore

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val price: Long,
    val oldPrice: Long? = null,
    val rating: Double,
    val description: String,
)

data class CartItem(val product: Product, val quantity: Int)

val sampleProducts = listOf(
    Product(1, "iPhone 16 Pro Max", "Điện thoại", 34_990_000, 37_990_000, 4.9,
        "Màn hình Super Retina XDR, hiệu năng mạnh mẽ và hệ thống camera chuyên nghiệp."),
    Product(2, "Samsung Galaxy S25 Ultra", "Điện thoại", 31_990_000, 33_990_000, 4.8,
        "Điện thoại cao cấp với bút S Pen, camera độ phân giải cao và Galaxy AI."),
    Product(3, "MacBook Air M4 13 inch", "Laptop", 26_990_000, 28_990_000, 4.9,
        "Laptop mỏng nhẹ, pin lâu, màn hình Liquid Retina và chip Apple Silicon."),
    Product(4, "ASUS Vivobook 15", "Laptop", 16_490_000, 18_490_000, 4.6,
        "Laptop học tập và văn phòng với màn hình lớn, bàn phím thoải mái."),
    Product(5, "iPad Air M3", "Máy tính bảng", 17_490_000, null, 4.8,
        "Máy tính bảng linh hoạt cho học tập, sáng tạo và giải trí."),
    Product(6, "Sony WH-1000XM5", "Phụ kiện", 7_490_000, 8_490_000, 4.7,
        "Tai nghe chống ồn chủ động, âm thanh chi tiết và thời lượng pin dài."),
)
