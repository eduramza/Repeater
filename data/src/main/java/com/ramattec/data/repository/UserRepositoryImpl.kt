package com.ramattec.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.data.local.MyPreferenceManager
import com.ramattec.data.mapper.toUser
import com.ramattec.data.mapper.toUserDto
import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.user.User
import com.ramattec.domain.repository.UserRepository
import com.ramattec.repeater.data.repository.USER_COLLECTION
import com.squareup.okhttp.Response
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val pref: MyPreferenceManager
): UserRepository{

    override suspend fun getName() = pref.getStoredName()

    override suspend fun updateUser(user: User) =
        suspendCoroutine<ResponseResult<User>> { continuation ->
            pref.setStoredName(user.name)
            fireStore.collection(USER_COLLECTION)
                .document(user.uid)
                .set(user.toUserDto())
                .addOnSuccessListener {
                    continuation.resume(ResponseResult.Success(user))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    //Login Session
    override suspend fun verifyIfUserIsLogged(): ResponseResult<User> {
        val user = auth.currentUser
        return if (user != null){
            ResponseResult.Success(user.toUser())
        } else {
            ResponseResult.Success(User())
        }
    }

    override suspend fun doLoginWithEmailAndPassword(
        email: String,
        password: String
    ): ResponseResult<User> = suspendCoroutine{ continuation ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(
                        ResponseResult.Success(
                            it.toUser()
                        )
                    )
                } ?: run{
                    continuation.resumeWithException(NullPointerException())
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun doLoginWithGoogleCredential(firebaseCredential: AuthCredential):
            ResponseResult<User> = suspendCoroutine{ continuation ->
        auth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(ResponseResult.Success(it.toUser()))
                } ?: run {
                    continuation.resume(ResponseResult.Success(User()))
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }

    }
}