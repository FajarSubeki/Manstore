package id.manstore.module.auth.domain.use_case

import id.manstore.core.util.Resource
import id.manstore.module.auth.domain.repository.LoginRepository

class LogoutUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return loginRepository.logout()
    }
}
