package com.ramattec.repeater.ui.login

import com.google.firebase.auth.AuthCredential

sealed class LoginEvent{
    data class EmailFilled(val email: String): LoginEvent()
    data class PasswordFilled(val password: String): LoginEvent()
    data class LoginWithEmailAndPassword(val email: String, val password: String): LoginEvent()
    data class LoginWithGoogleAccount(val credential: AuthCredential): LoginEvent()
    object IsUserLogged: LoginEvent()
}
