package id.manstore.module.cart.domain.use_case

import com.google.gson.Gson
import id.manstore.core.util.Resource
import id.manstore.module.auth.data.dto.UserResponseDto
import id.manstore.module.auth.data.local.AuthPreferences
import id.manstore.module.cart.domain.model.CartProduct
import id.manstore.module.cart.domain.repository.CartRepository
import id.manstore.module.product.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AddCartItemsUseCase(
    private val cartRepository: CartRepository,
    private val authPreferences: AuthPreferences,
    private val gson: Gson
    ) {
    suspend operator fun invoke(id: Int, products: List<Product>): Flow<Resource<CartProduct>> {
        val data = authPreferences.getUserData.first()
        val user = gson.fromJson(data, UserResponseDto::class.java)
        return cartRepository.addCartItems(id, user.id, products)
    }
}
