package id.manstore.module.product.presentation.home

import id.manstore.module.product.domain.model.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)