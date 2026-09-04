package com.tranduytruong.novatech.core.data.repository

import com.tranduytruong.novatech.core.data.fake.FakeProductDataSource
import com.tranduytruong.novatech.core.domain.model.Product
import com.tranduytruong.novatech.core.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataSource: FakeProductDataSource,
) : ProductRepository {
    override fun getProducts(): List<Product> = dataSource.products

    override fun getProduct(id: Int): Product? = dataSource.products.firstOrNull { it.id == id }

    override fun getCategories(): List<String> =
        listOf("Tất cả") + dataSource.products.map(Product::category).distinct()
}
