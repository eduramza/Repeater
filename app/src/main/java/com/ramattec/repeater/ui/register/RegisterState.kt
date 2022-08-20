package com.ramattec.repeater.ui.register

import com.ramattec.domain.model.user.User

sealed class RegisterState{
    object Loading: RegisterState()
    data class Error(val isPasswordError: Boolean, val isEmailError: Boolean): RegisterState()
    data class Success(val user: User): RegisterState()
    object Typing: RegisterState()
    object NetworkError: RegisterState()
}