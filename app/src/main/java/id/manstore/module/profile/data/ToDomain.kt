package id.manstore.module.profile.data

import id.manstore.module.auth.data.dto.UserResponseDto
import id.manstore.module.auth.domain.model.User

internal fun UserResponseDto.toDomain(): User {
    return User(
        address = address,
        email = email,
        id = id,
        name = name,
        password = password,
        phone = phone,
        username = username
    )
}