package id.manstore.module.profile.data.repository

import id.manstore.module.auth.data.local.AuthPreferences
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val authPreferences: AuthPreferences) {
    fun getUserProfile(): Flow<String> {
        return authPreferences.getUserData
    }
}