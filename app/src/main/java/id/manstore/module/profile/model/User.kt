package id.manstore.module.profile.model

import id.manstore.module.auth.data.dto.Address
import id.manstore.module.auth.data.dto.Name

data class User(
    val address: Address? = null,
    val email: String? = null,
    val id: Int? = null,
    val name: Name? = null,
    val password: String? = null,
    val phone: String? = null,
    val username: String? = null
)
