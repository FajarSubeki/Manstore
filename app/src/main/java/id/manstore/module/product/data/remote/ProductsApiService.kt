package id.manstore.module.product.data.remote

import id.manstore.module.product.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductsApiService {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/categories")
    suspend fun getProductCategories(): List<String>
}