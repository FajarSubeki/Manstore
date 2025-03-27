package id.manstore.module.product.domain.repository

import id.manstore.core.util.Resource
import id.manstore.module.product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getProductCategories(): List<String>
}