package com.ramattec.repeater.ui.register

import com.ramattec.repeater.domain.entity.register.UserEntity

data class RegisterUIState(
    val newUser: UserEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)