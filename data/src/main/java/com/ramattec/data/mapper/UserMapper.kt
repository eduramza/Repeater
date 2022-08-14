package com.ramattec.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.ramattec.data.model.user.UserDto
import com.ramattec.domain.model.user.User

fun FirebaseUser.toUser() =
    User(
        uid = uid,
        name = displayName ?: "",
        email = email ?: "",
        phone = phoneNumber ?: "",
        photo = phoneNumber ?: "",
        isLogged = true
    )

fun User.toUserDto() =
    UserDto(
        firebaseId = uid,
        name = name,
        email = email,
        phoneNumber = phone,
        photoUrl = photo
    )