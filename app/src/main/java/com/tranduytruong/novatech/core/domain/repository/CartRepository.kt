package com.tranduytruong.novatech.core.domain.repository

import com.tranduytruong.novatech.core.domain.model.CartItem
import com.tranduytruong.novatech.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun add(product: Product)
    suspend fun changeQuantity(productId: Int, amount: Int)
    suspend fun clear()
}
