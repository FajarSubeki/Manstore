package id.manstore.module.cart.data.repository

import id.manstore.module.cart.domain.model.CartProduct
import id.manstore.module.cart.domain.repository.CartRepository
import id.manstore.core.util.Resource
import id.manstore.module.cart.data.remote.CartApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class CartRepositoryImpl(
    private val cartApiService: CartApiService
) : CartRepository {
    override suspend fun getAllCartItems(id: Int): Flow<Resource<List<CartProduct>>> = flow {
        Timber.d("Get all cart items called")
        emit(Resource.Loading())
        try {
            val response = cartApiService.cartItems(id)
            val cartItems = ArrayList<CartProduct>()
            response.forEach { it1 ->
                it1.cartProductDtos.forEach {
                    val productResponse = cartApiService.product(it.productId)
                    val cartProduct =
                        CartProduct(
                            productResponse.title,
                            productResponse.price,
                            it.quantity,
                            productResponse.image
                        )
                    cartItems.add(cartProduct)
                }
            }
            emit(Resource.Success(cartItems.toList().distinctBy { it.name }))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Could not reach the server, please check your internet connection!"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, something went wrong!"))
        }
    }
}