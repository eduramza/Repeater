package com.ramattec.domain.model.user

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val photo: String = "",
    val isLogged: Boolean = false
)