package com.ramattec.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.ramattec.data.mapper.toUser
import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.user.User
import com.ramattec.domain.repository.RegisterRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): RegisterRepository{

    override suspend fun createNewUser(email: String, password: String) =
        suspendCoroutine<NetworkResult<User>> { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    authResult.user?.let { firebaseUser->
                        continuation.resume(NetworkResult.Success(firebaseUser.toUser()))
                    }
                }
                .addOnFailureListener {
                    continuation.resume(NetworkResult.Failure(it))
                }
        }
}