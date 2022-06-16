package com.ramattec.repeater.domain.repository

import com.google.firebase.auth.AuthCredential
import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.user.UserEntity

interface LoginRepository {
    fun verifyIfUserIsLogged(): Result<UserEntity>
    suspend fun doLoginWithEmailAndPassword(email: String, password: String): Result<UserEntity>
    suspend fun sigInWithGoogleCredential(firebaseCredential: AuthCredential): Result<FirebaseUserEntity>
    suspend fun saveNewUserOnFireStore(firebaseUser: FirebaseUserEntity): Result<UserEntity>
}