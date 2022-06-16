package com.ramattec.repeater.data.repository.login

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.repeater.data.model.user.UserFireStoreModel
import com.ramattec.repeater.data.repository.USER_COLLECTION
import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
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
            Result<FirebaseUserEntity> = suspendCoroutine { continuation ->
        firebaseAuth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(
                        Result.success(
                            FirebaseUserEntity(
                                uid = it.uid,
                                name = it.displayName,
                                email = it.email,
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

    override suspend fun saveNewUserOnFireStore(firebaseUser: FirebaseUserEntity):
            Result<UserEntity> = suspendCoroutine { continuation ->
        val user = UserFireStoreModel(
            firebaseId = firebaseUser.uid,
            name = firebaseUser.name!!,
            email = firebaseUser.email!!,
            phoneNumber = firebaseUser.phoneNumber,
            photoUrl = firebaseUser.photoUrl.toString()
        )
        fireStore.collection(USER_COLLECTION)
            .add(user)
            .addOnSuccessListener {
                continuation.resume(
                    Result.success(user.toEntity())
                )
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }
}