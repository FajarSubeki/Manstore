package id.manstore.module.cart.data.remote.dto

import com.google.gson.annotations.SerializedName
import id.manstore.module.cart.data.remote.dto.CartProductDto

data class UserCartResponseDto(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val cartProductDtos: List<CartProductDto>,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("__v")
    val v: Int
)