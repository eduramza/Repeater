package com.ramattec.repeater.domain.login

sealed class LoginUIState {
    object Progress: LoginUIState()
    data class Success<T>(val data: T): LoginUIState()
    data class Failure(val cause: String?): LoginUIState()
    object NotLogged: LoginUIState()
}