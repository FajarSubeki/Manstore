package id.manstore.module.product.data.repository

import id.manstore.module.product.data.remote.ProductsApiService
import id.manstore.module.product.data.remote.mappers.toDomain
import id.manstore.module.product.domain.model.Product
import id.manstore.module.product.domain.repository.ProductsRepository
import id.manstore.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductsRepositoryImpl(private val productsApiService: ProductsApiService) :
    ProductsRepository {
    override suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val response = productsApiService.getProducts()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Could not reach the server, please check your internet connection!"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, something went wrong!"))
        }
    }

    override suspend fun getProductCategories(): List<String> {
        return productsApiService.getProductCategories()
    }
}