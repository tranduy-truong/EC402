package com.tranduytruong.novatech.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.tranduytruong.novatech.core.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CartDao {
    @Query("SELECT * FROM cart_items ORDER BY productId")
    abstract fun observeAll(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    abstract suspend fun getByProductId(productId: Int): CartItemEntity?

    @Upsert
    abstract suspend fun upsert(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    abstract suspend fun deleteByProductId(productId: Int)

    @Query("DELETE FROM cart_items")
    abstract suspend fun clear()

    @Transaction
    open suspend fun addOrIncrement(item: CartItemEntity) {
        val current = getByProductId(item.productId)
        upsert(item.copy(quantity = (current?.quantity ?: 0) + 1))
    }
}
