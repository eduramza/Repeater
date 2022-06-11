package com.ramattec.repeater.data.auth

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.ramattec.repeater.di.AuthProvider
import com.ramattec.repeater.ui.login.LoggedUserView
import com.ramattec.repeater.ui.login.LoginResult
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun doLoginWithEmailAndPassword(
        email: String,
        password: String
    ): LoginResult {
        var result = LoginResult()
        try {
            val user = AuthProvider.loginWithEmailAndPassword(email, password)
            if (user != null) {
                result = LoginResult(
                    success = LoggedUserView(
                        user.displayName
                            ?: "repositoryUser"
                    )
                )
                Log.d("RepositoryDebug", "RepositoryUserSuccessResponse")
            }
        } catch (e: Exception) {
            result = LoginResult(error = e.message.toString())
        }
        return result
    }

    fun verifyUserLogged(): Boolean = (firebaseAuth.currentUser != null)

    suspend fun sigInWithGoogleCredential(firebaseCredential: AuthCredential): LoginResult {
        var result = LoginResult()
        try {
            val user = AuthProvider.loginWithGoogleCredentials(firebaseCredential)
            if (user != null) {
                result = LoginResult(
                    success = LoggedUserView(
                        user.displayName
                            ?: "repositoryUser"
                    )
                )
                Log.d("RepositoryDebug", "Google Repository Login With Success!")
            }
        } catch (e: Exception) {
            result = LoginResult(error = e.message.toString())
        }
        return result
    }
}