package id.manstore.module.cart.di

import com.google.gson.Gson
import id.manstore.module.cart.data.remote.CartApiService
import id.manstore.module.cart.data.repository.CartRepositoryImpl
import id.manstore.module.cart.domain.repository.CartRepository
import id.manstore.module.cart.domain.use_case.GetCartItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.manstore.core.util.Constants
import id.manstore.module.auth.data.local.AuthPreferences
import id.manstore.module.cart.domain.use_case.AddCartItemsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    @Singleton
    fun provideCartApiService(): CartApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartApiService: CartApiService
    ): CartRepository {
        return CartRepositoryImpl(
            cartApiService
        )
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(
        cartRepository: CartRepository,
        authPreferences: AuthPreferences,
        gson: Gson
    ): GetCartItemsUseCase {
        return GetCartItemsUseCase(cartRepository, authPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideAddCartItemsUseCase(
        cartRepository: CartRepository,
        authPreferences: AuthPreferences,
        gson: Gson
    ): AddCartItemsUseCase {
        return AddCartItemsUseCase(cartRepository, authPreferences, gson)
    }
}