package com.ramattec.repeater.data.repository.login

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.repeater.data.model.user.UserFireStoreModel
import com.ramattec.repeater.data.repository.USER_COLLECTION
import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import com.ramattec.repeater.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : LoginRepository {

    override fun verifyIfUserIsLogged(): Result<UserEntity> {
        val user = firebaseAuth.currentUser
        return if (user != null)
            Result.success(
                UserEntity(
                    user.displayName!!, user.email!!,
                    user.phoneNumber, user.photoUrl.toString(),
                    true
                )
            )
        else
            Result.success(UserEntity.userNotLogged())
    }

    override suspend fun doLoginWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserEntity> = suspendCoroutine { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(
                        Result.success(
                            UserEntity(
                                name = it.displayName,
                                email = it.email!!,
                                phone = it.phoneNumber,
                                photo = it.photoUrl.toString(),
                                isLogged = true
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

    override suspend fun sigInWithGoogleCredential(firebaseCredential: AuthCredential):
            Result<UserFormEntity> = suspendCoroutine { continuation ->
        firebaseAuth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(
                        Result.success(
                            UserFormEntity(
                                name = it.displayName!!,
                                email = it.email!!,
                                password = "",
                                phoneNumber = it.phoneNumber,
                                photoUrl = it.photoUrl.toString()
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
}