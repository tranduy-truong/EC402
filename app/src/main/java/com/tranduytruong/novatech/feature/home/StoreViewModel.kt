package com.tranduytruong.novatech.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranduytruong.novatech.core.domain.model.CartItem
import com.tranduytruong.novatech.core.domain.model.Product
import com.tranduytruong.novatech.core.domain.repository.CartRepository
import com.tranduytruong.novatech.core.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    var query by mutableStateOf("")
    var selectedCategory by mutableStateOf("Tất cả")

    var cartItems by mutableStateOf<List<CartItem>>(emptyList())
        private set

    val categories: List<String> = productRepository.getCategories()

    val products: List<Product>
        get() = productRepository.getProducts().filter { product ->
            (selectedCategory == "Tất cả" || product.category == selectedCategory) &&
                product.name.contains(query, ignoreCase = true)
        }

    val cartCount: Int
        get() = cartItems.sumOf(CartItem::quantity)

    val total: Long
        get() = cartItems.sumOf { it.product.price * it.quantity }

    init {
        viewModelScope.launch {
            cartRepository.observeCart().collectLatest { items ->
                cartItems = items
            }
        }
    }

    fun product(id: Int): Product? = productRepository.getProduct(id)

    fun addToCart(product: Product) {
        viewModelScope.launch { cartRepository.add(product) }
    }

    fun changeQuantity(productId: Int, amount: Int) {
        viewModelScope.launch { cartRepository.changeQuantity(productId, amount) }
    }

    fun clearCart() {
        viewModelScope.launch { cartRepository.clear() }
    }
}
