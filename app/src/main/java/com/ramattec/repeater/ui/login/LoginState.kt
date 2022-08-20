package com.ramattec.repeater.ui.login

import com.ramattec.domain.model.user.User

sealed class LoginState {
    object Initial: LoginState()
    object Progress: LoginState()
    data class UserLogged(val user: User): LoginState()
    object PasswordError: LoginState()
    object EmailError: LoginState()
    object NetworkError: LoginState()
}
