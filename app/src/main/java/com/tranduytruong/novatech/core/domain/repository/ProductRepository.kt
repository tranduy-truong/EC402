package com.tranduytruong.novatech.core.domain.repository

import com.tranduytruong.novatech.core.domain.model.Product

interface ProductRepository {
    fun getProducts(): List<Product>
    fun getProduct(id: Int): Product?
    fun getCategories(): List<String>
}
