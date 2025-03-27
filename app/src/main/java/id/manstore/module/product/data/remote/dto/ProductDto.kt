package id.manstore.module.product.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val ratingDto: RatingDto? = null,
    @SerializedName("title")
    val title: String
)

data class AddCartRequestDto(
    val id: Int,
    val userId: Int,
    val products: List<ProductDto>
)

data class CartProduct(
    val id: Int,
    val userId: Int,
    val products: List<ProductDto>
)