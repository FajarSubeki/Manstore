package id.manstore.module.cart.domain.model

data class CartProduct(
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String
)
