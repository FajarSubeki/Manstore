package id.manstore.module.product.di

import id.manstore.module.product.data.remote.ProductsApiService
import id.manstore.module.product.data.repository.ProductsRepositoryImpl
import id.manstore.module.product.domain.repository.ProductsRepository
import id.manstore.module.product.domain.use_case.GetCategoriesUseCase
import id.manstore.module.product.domain.use_case.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.manstore.core.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    @Singleton
    fun provideProductsApiService(): ProductsApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        productsApiService: ProductsApiService
    ): ProductsRepository {
        return ProductsRepositoryImpl(
            productsApiService
        )
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(productsRepository: ProductsRepository): GetProductsUseCase {
        return GetProductsUseCase(productsRepository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(productsRepository: ProductsRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(productsRepository)
    }
}