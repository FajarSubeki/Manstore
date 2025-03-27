package id.manstore.module.cart.data.remote

import id.manstore.module.cart.data.remote.dto.UserCartResponseDto
import id.manstore.module.cart.domain.model.CartProduct
import id.manstore.module.product.data.remote.dto.AddCartRequestDto
import id.manstore.module.product.data.remote.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {
    @GET("carts/user/{id}")
    suspend fun cartItems(
        @Path("id") id: Int
    ): List<UserCartResponseDto>

    @GET("products/{id}")
    suspend fun product(
        @Path("id") id: Int
    ): ProductDto

    @POST("carts")
    suspend fun addCartItem(
        @Body request: AddCartRequestDto
    ): CartProduct
}