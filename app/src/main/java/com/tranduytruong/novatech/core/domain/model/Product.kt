package com.tranduytruong.novatech.core.domain.model

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val imageRes: Int,
    val price: Long,
    val oldPrice: Long? = null,
    val rating: Double,
    val description: String,
)

data class CartItem(
    val product: Product,
    val quantity: Int,
)
