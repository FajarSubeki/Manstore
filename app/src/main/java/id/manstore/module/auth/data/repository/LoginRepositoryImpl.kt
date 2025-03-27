package id.manstore.module.auth.data.repository

import id.manstore.module.auth.data.dto.UserResponseDto
import id.manstore.module.auth.data.local.AuthPreferences
import id.manstore.module.auth.data.remote.AuthApiService
import id.manstore.module.auth.data.remote.request.LoginRequest
import id.manstore.module.auth.domain.repository.LoginRepository
import id.manstore.core.util.Resource
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class LoginRepositoryImpl(
    private val authApiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Resource<Unit> {
        Timber.d("Login called")
        return try {
            val response = authApiService.loginUser(loginRequest)
            Timber.d("Login Token: ${response.token}")

            getAllUsers(loginRequest.username)?.let { authPreferences.saveUserdata(it) }

            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(message = "Could not reach the server, please check your internet connection!")
        } catch (e: HttpException) {
            Resource.Error(message = "An Unknown error occurred, please try again!")
        }
    }

    override suspend fun autoLogin(): Resource<Unit> {
        val accessToken = authPreferences.getAccessToken.first()
        Timber.d("Auto login access token: $accessToken")
        return if (accessToken != "") {
            Resource.Success(Unit)
        } else {
            Resource.Error("")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            authPreferences.clearAccessToken()
            val fetchedToken = authPreferences.getAccessToken.first()
            Timber.d("token: $fetchedToken")

            if (fetchedToken.isEmpty()) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Unknown Error")
            }
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
    }

    private suspend fun getAllUsers(name: String): UserResponseDto? {
        val response = authApiService.getAllUsers()
        return response.find { it.username == name }
    }
}