package com.ramattec.repeater.domain.entity.user

data class FirebaseUserEntity(
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val photoUrl: String? = null
)
