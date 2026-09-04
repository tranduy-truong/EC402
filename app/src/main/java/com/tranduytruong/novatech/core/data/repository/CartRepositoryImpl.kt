package com.tranduytruong.novatech.core.data.repository

import com.tranduytruong.novatech.core.data.local.dao.CartDao
import com.tranduytruong.novatech.core.data.local.entity.CartItemEntity
import com.tranduytruong.novatech.core.domain.model.CartItem
import com.tranduytruong.novatech.core.domain.model.Product
import com.tranduytruong.novatech.core.domain.repository.CartRepository
import com.tranduytruong.novatech.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CartRepository {
    override fun observeCart(): Flow<List<CartItem>> =
        cartDao.observeAll().map { items -> items.map(CartItemEntity::toDomain) }

    override suspend fun add(product: Product) = withContext(ioDispatcher) {
        cartDao.addOrIncrement(product.toEntity(quantity = 1))
    }

    override suspend fun changeQuantity(productId: Int, amount: Int) = withContext(ioDispatcher) {
        val current = cartDao.getByProductId(productId) ?: return@withContext
        val newQuantity = current.quantity + amount
        if (newQuantity <= 0) {
            cartDao.deleteByProductId(productId)
        } else {
            cartDao.upsert(current.copy(quantity = newQuantity))
        }
    }

    override suspend fun clear() = withContext(ioDispatcher) {
        cartDao.clear()
    }
}

private fun Product.toEntity(quantity: Int) = CartItemEntity(
    productId = id,
    name = name,
    category = category,
    imageRes = imageRes,
    price = price,
    oldPrice = oldPrice,
    rating = rating,
    description = description,
    quantity = quantity,
)

private fun CartItemEntity.toDomain() = CartItem(
    product = Product(
        id = productId,
        name = name,
        category = category,
        imageRes = imageRes,
        price = price,
        oldPrice = oldPrice,
        rating = rating,
        description = description,
    ),
    quantity = quantity,
)
