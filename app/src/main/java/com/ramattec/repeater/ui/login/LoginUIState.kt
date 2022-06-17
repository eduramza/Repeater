package com.ramattec.repeater.ui.login

import com.ramattec.repeater.domain.entity.user.UserEntity

data class LoginUIState(
    val loggedUser: UserEntity? = null,
    val isLoadingUser: Boolean = false,
    val isLoadingInitialUser: Boolean = true,
    val isEmailInputValid: Boolean = true,
    val isPasswordInputValid: Boolean = true,
    val loginError: String? = null
)
