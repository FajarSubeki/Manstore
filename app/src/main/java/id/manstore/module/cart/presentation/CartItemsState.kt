package id.manstore.module.cart.presentation

import id.manstore.module.cart.domain.model.CartProduct

data class CartItemsState(
    val cartItems: List<CartProduct> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)