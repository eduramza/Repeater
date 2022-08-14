package com.ramattec.domain.repository

import com.google.firebase.auth.AuthCredential
import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.user.User

interface UserRepository {
    suspend fun getName(): String

    suspend fun updateUser(user: User): ResponseResult<User>

    suspend fun verifyIfUserIsLogged(): ResponseResult<User>

    suspend fun doLoginWithEmailAndPassword(email: String, password: String): ResponseResult<User>

    suspend fun doLoginWithGoogleCredential(firebaseCredential: AuthCredential):
            ResponseResult<User>
}