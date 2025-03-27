package id.manstore.module.auth.domain.use_case

import id.manstore.module.auth.data.remote.request.LoginRequest
import id.manstore.module.auth.domain.model.LoginResult
import id.manstore.module.auth.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
    ): LoginResult {
        val usernameError = if (username.isBlank()) "User name cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null


        if (usernameError != null) {
            return LoginResult(
                usernameError = usernameError
            )
        }

        if (passwordError != null) {
            return LoginResult(
                passwordError = passwordError
            )
        }


        val loginRequest = LoginRequest(
            username = username.trim(),
            password = password.trim()
        )

        return LoginResult(
            result = loginRepository.login(loginRequest)
        )
    }
}