package id.manstore.module.auth.di

import id.manstore.module.auth.data.local.AuthPreferences
import id.manstore.module.auth.data.remote.AuthApiService
import id.manstore.module.auth.data.repository.LoginRepositoryImpl
import id.manstore.module.auth.domain.repository.LoginRepository
import id.manstore.module.auth.domain.use_case.AutoLoginUseCase
import id.manstore.module.auth.domain.use_case.LoginUseCase
import id.manstore.module.auth.domain.use_case.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.manstore.core.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        authApiService: AuthApiService,
        authPreferences: AuthPreferences
    ): LoginRepository {
        return LoginRepositoryImpl(
            authApiService = authApiService,
            authPreferences = authPreferences
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideAutoLoginUseCase(loginRepository: LoginRepository): AutoLoginUseCase {
        return AutoLoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(loginRepository: LoginRepository): LogoutUseCase {
        return LogoutUseCase(loginRepository)
    }
}
