package id.manstore.module.auth.domain.repository

import id.manstore.core.util.Resource
import id.manstore.module.auth.data.remote.request.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Resource<Unit>
    suspend fun autoLogin(): Resource<Unit>
    suspend fun logout(): Resource<Unit>
}
