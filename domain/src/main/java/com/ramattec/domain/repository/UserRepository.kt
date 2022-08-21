package com.ramattec.domain.repository

import com.google.firebase.auth.AuthCredential
import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.user.User

interface UserRepository {
    fun getName(): String

    suspend fun updateUser(user: User): NetworkResult<User>

    suspend fun verifyIfUserIsLogged(): NetworkResult<User>

    suspend fun doLoginWithEmailAndPassword(email: String, password: String): NetworkResult<User>

    suspend fun doLoginWithGoogleCredential(firebaseCredential: AuthCredential):
            NetworkResult<User>
}