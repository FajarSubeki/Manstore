package id.manstore.module.product.domain.use_case

import id.manstore.core.util.Resource
import id.manstore.module.product.domain.model.Product
import id.manstore.module.product.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> {
        return productsRepository.getProducts()
    }
}