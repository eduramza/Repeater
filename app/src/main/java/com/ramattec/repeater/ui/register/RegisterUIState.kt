package com.ramattec.repeater.ui.register

import com.ramattec.repeater.domain.entity.user.UserEntity

data class RegisterUIState(
    val newUser: UserEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)