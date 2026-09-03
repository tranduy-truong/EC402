package com.example.techstore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StoreViewModel : ViewModel() {
    var query by mutableStateOf("")
    var selectedCategory by mutableStateOf("Tất cả")
    private val cart = mutableStateListOf<CartItem>()

    val categories = listOf("Tất cả", "Điện thoại", "Laptop", "Máy tính bảng", "Phụ kiện")
    val products: List<Product>
        get() = sampleProducts.filter {
            (selectedCategory == "Tất cả" || it.category == selectedCategory) &&
                it.name.contains(query, ignoreCase = true)
        }
    val cartItems: List<CartItem> get() = cart
    val cartCount: Int get() = cart.sumOf { it.quantity }
    val total: Long get() = cart.sumOf { it.product.price * it.quantity }

    fun product(id: Int) = sampleProducts.firstOrNull { it.id == id }

    fun addToCart(product: Product) {
        val index = cart.indexOfFirst { it.product.id == product.id }
        if (index < 0) cart.add(CartItem(product, 1))
        else cart[index] = cart[index].copy(quantity = cart[index].quantity + 1)
    }

    fun changeQuantity(productId: Int, amount: Int) {
        val index = cart.indexOfFirst { it.product.id == productId }
        if (index < 0) return
        val newQuantity = cart[index].quantity + amount
        if (newQuantity <= 0) cart.removeAt(index)
        else cart[index] = cart[index].copy(quantity = newQuantity)
    }
}
