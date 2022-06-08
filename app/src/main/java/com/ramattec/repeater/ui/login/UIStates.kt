package com.ramattec.repeater.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedUserView? = null,
    val error: Int? = null
)