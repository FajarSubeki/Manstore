package id.manstore.module.auth.data.remote

import id.manstore.module.auth.data.dto.UserResponseDto
import id.manstore.module.auth.data.remote.request.LoginRequest
import id.manstore.module.auth.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponseDto>
}