package com.ramattec.repeater.domain.entity.user

data class UserFormEntity(
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String? = null,
    val photoUrl: String? = null
)
