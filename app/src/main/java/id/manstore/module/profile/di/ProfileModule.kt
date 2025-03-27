package id.manstore.module.profile.di

import id.manstore.module.profile.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.manstore.module.auth.data.local.AuthPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileRepository(authPreferences: AuthPreferences): ProfileRepository {
        return ProfileRepository(authPreferences)
    }
}