package com.ramattec.repeater.ui.login

import com.ramattec.repeater.domain.entity.user.UserEntity

data class LoginUIState(
    val loggedUser: UserEntity? = null,
    val isLoadingUser: Boolean = false,
    val isLoadingInitialUser: Boolean = true,
    val isEmailInputInvalid: Boolean = false,
    val isPasswordInputInvalid: Boolean = false,
    val loginError: String? = null
)
