package com.tranduytruong.novatech.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    val name: String,
    val category: String,
    val imageRes: Int,
    val price: Long,
    val oldPrice: Long?,
    val rating: Double,
    val description: String,
    val quantity: Int,
)
