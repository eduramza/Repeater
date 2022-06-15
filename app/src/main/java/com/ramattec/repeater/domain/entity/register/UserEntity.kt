package com.ramattec.repeater.domain.entity.register

data class UserEntity(
    val name: String,
    val email: String,
    val phone: String?,
    val photo: String?
)