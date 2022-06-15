package com.ramattec.repeater.data.repository.login

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.ramattec.repeater.data.model.user.UserModel
import com.ramattec.repeater.di.AuthProvider
import com.ramattec.repeater.domain.entity.register.UserFormEntity
import com.ramattec.repeater.ui.login.LoggedUserView
import com.ramattec.repeater.ui.login.LoginResult
import java.lang.NullPointerException
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun doLoginWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserFormEntity> = suspendCoroutine { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(
                        Result.success(
                            UserFormEntity(
                                firebaseId = it.uid,
                                name = it.displayName!!,
                                email = it.email,
                            )
                        )
                    )
                } ?: run {
                    continuation.resumeWithException(NullPointerException())
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
//                fireStoreUserRepository.updateOrCreateUser(
//                    UserModel(
//                        firebaseId = user.uid, name = user.displayName ?: "Ovonildo",
//                        email = user.email, phoneNumber = user.phoneNumber,
//                        decks = Deck(
//                            deckId = UUID.randomUUID().toString(),
//                            name = "Primeiro Deck Criado",
//                            theme = "Inglês",
//                            description = "Deck para testar o inglês nivel básico",
//                            R.color.colorAccentVariant
//                        )
//                    )
//                )

    fun verifyUserLogged(): Result<UserModel?> {
        val user = firebaseAuth.currentUser
        return if (user != null)
            Result.success(
                UserModel(
                    firebaseId = user.uid,
                    name = user.displayName!!,
                    email = user.email
                )
            )
        else
            Result.success(null)
    }

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