package com.ramattec.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.data.local.MyPreferenceManager
import com.ramattec.data.mapper.toUser
import com.ramattec.data.mapper.toUserDto
import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.user.User
import com.ramattec.domain.repository.UserRepository
import com.ramattec.repeater.data.repository.USER_COLLECTION
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val pref: MyPreferenceManager
): UserRepository{

    override fun getName() = pref.getStoredName()

    override suspend fun updateUser(user: User) =
        suspendCoroutine<NetworkResult<User>> { continuation ->
            pref.setStoredName(user.name)
            fireStore.collection(USER_COLLECTION)
                .document(user.uid)
                .set(user.toUserDto())
                .addOnSuccessListener {
                    continuation.resume(NetworkResult.Success(user))
                }
                .addOnFailureListener {
                    continuation.resume(NetworkResult.Failure(it))
                }
        }

    //Login Session
    override suspend fun verifyIfUserIsLogged(): NetworkResult<User> {
        val user = auth.currentUser
        return if (user != null){
            NetworkResult.Success(user.toUser())
        } else {
            NetworkResult.Success(User())
        }
    }

    override suspend fun doLoginWithEmailAndPassword(
        email: String,
        password: String
    ): NetworkResult<User> = suspendCoroutine{ continuation ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(
                        NetworkResult.Success(
                            it.toUser()
                        )
                    )
                } ?: run{
                    continuation.resume(NetworkResult.Failure(NullPointerException()))
                }
            }
            .addOnFailureListener {
                continuation.resume(NetworkResult.Failure(it))
            }
    }

    override suspend fun doLoginWithGoogleCredential(firebaseCredential: AuthCredential):
            NetworkResult<User> = suspendCoroutine{ continuation ->
        auth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(NetworkResult.Success(it.toUser()))
                } ?: run {
                    continuation.resume(NetworkResult.Success(User()))
                }
            }
            .addOnFailureListener {
                continuation.resume(NetworkResult.Failure(it))
            }
    }
}