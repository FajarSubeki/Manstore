package id.manstore.module.cart.domain.repository

import id.manstore.core.util.Resource
import id.manstore.module.cart.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getAllCartItems(id: Int): Flow<Resource<List<CartProduct>>>
}